package br.com.jujuhealth.physio.data.request.auth

import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.User
import br.com.jujuhealth.physio.data.request.COLLECTION_PHYSIOS
import br.com.jujuhealth.physio.data.request.COLLECTION_USERS
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Timestamp

class ServiceAuthImpl(private val auth: FirebaseAuth, private val database: FirebaseFirestore) :
    ServiceAuthContract {

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

    override suspend fun updatePassword(
        pwdActual: String,
        pwd: String,
        success: () -> Unit,
        error: () -> Unit
    ) {
        try {
            auth.signInWithEmailAndPassword(auth.currentUser?.email!!, pwdActual).runCatching {
                this.user?.let {
                    success.invoke()
                } ?: error.invoke()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error.invoke()
        }
    }

    override suspend fun getUser(success: (User?) -> Unit, error: () -> Unit) {
        auth.currentUser?.uid?.let { uid ->
            try {
                database.collection(COLLECTION_PHYSIOS).document(uid).get().runCatching {
                    val user = data<User>()
                    user.uId = uid
                    success.invoke(user)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                error.invoke()
            }
        }
    }

    override fun checkUserLogged(
        success: (FirebaseUser?) -> Unit,
        error: () -> Unit
    ) {
        auth.currentUser?.let {
            success(it)
        } ?: run {
            error()
        }
    }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String,
        success: (Patient) -> Unit,
        error: () -> Unit
    ) {
        try {
            val patient = Patient(
                name = name,
                email = email,
                providerId = auth.currentUser?.providerId,
                uId = auth.currentUser?.uid
            )
            val result = auth.createUserWithEmailAndPassword(email, password).runCatching {
                val id = this.user?.uid ?: run { email }

                database.collection(COLLECTION_USERS).document(id).set(
                    patient
                ).runCatching {
                    success(patient)
                }
            }
            if (result.isSuccess) {
                success(patient)
            } else {
                error()
            }
        } catch (e: Exception) {
            error.invoke()
        }
    }

}
