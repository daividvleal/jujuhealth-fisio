package br.com.jujuhealth.physio.data.request.auth

import br.com.jujuhealth.physio.data.model.User
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore

class ServiceAuth(private val auth: FirebaseAuth, private val database: FirebaseFirestore) :
    ServiceAuthContract {

    override suspend fun signIn(
        email: String,
        password: String,
        success: (User?) -> Unit,
        error: () -> Unit
    ) {
        val task = auth.signInWithEmailAndPassword(email, password)
        try {
            task.runCatching {
                this.user?.let { firebaseUser ->
                    firebaseUser.let {
                        success.invoke(User.buildUser(firebaseUser))
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

}
