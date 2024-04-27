package br.com.jujuhealth.physio.data.request.auth

import br.com.jujuhealth.physio.data.domain.Patient
import br.com.jujuhealth.physio.data.domain.User
import br.com.jujuhealth.physio.data.request.COLLECTION_PHYSIOS
import br.com.jujuhealth.physio.data.request.COLLECTION_USERS
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore

class ServiceAuthImpl(private val auth: FirebaseAuth, private val database: FirebaseFirestore) :
    ServiceAuthContract {

    private lateinit var currentUser: User

    override suspend fun signIn(
        email: String,
        password: String,
        success: () -> Unit,
        error: () -> Unit
    ) {
        try {
            val task = auth.signInWithEmailAndPassword(email, password)
            task.runCatching {
                this.user?.let { firebaseUser ->
                    firebaseUser.let {
                        success.invoke()
                    }
                } ?: error.invoke()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error.invoke()
        }
    }

    override suspend fun signOut(error: () -> Unit) {
        try {
            auth.signOut().runCatching { }
            database.clearPersistence().runCatching { }
        } catch (e: Exception) {
            e.printStackTrace()
            error.invoke()
        }
    }

    override suspend fun updateUser(
        user: User,
        success: (user: User) -> Unit,
        error: () -> Unit
    ) {
        val resultUpdateUser =
            database.collection(COLLECTION_PHYSIOS)
                .document(currentUser.uId.orEmpty())
                .update(
                    user
                ).runCatching {
                    currentUser = user
                }
        if (resultUpdateUser.isSuccess) {
            success(user)
        } else {
            error()
        }
    }

    override suspend fun getUser(success: (User?) -> Unit, error: () -> Unit) {
        auth.currentUser?.uid?.let { uid ->
            try {
                database.collection(COLLECTION_PHYSIOS).document(uid).get().runCatching {
                    val user = data<User>()
                    user.uId = uid
                    currentUser = user
                    success.invoke(user)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                error.invoke()
            }
        }
    }

    override suspend fun createPatient(
        name: String,
        email: String,
        password: String,
        userPwd: String,
        success: (Patient, User) -> Unit,
        error: () -> Unit
    ) {
        try {
            val patient = Patient(
                name = name,
                email = email
            )
            val task = auth.createUserWithEmailAndPassword(email, password).runCatching {
                val id = this.user?.uid.orEmpty()
                patient.uId = id
                patient.providerId = this.user?.providerId.orEmpty()
                database.collection(COLLECTION_USERS).document(id).set(
                    patient
                )
            }
            val signOutTask = auth.signOut().runCatching { }
            if (task.isSuccess && signOutTask.isSuccess) {
                signIn(currentUser.email.orEmpty(), userPwd, {
                    success(patient, currentUser)
                }, error)
            } else {
                error()
            }
        } catch (e: Exception) {
            error.invoke()
        }
    }
}