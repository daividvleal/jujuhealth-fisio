package br.com.jujuhealth.physio.data.request.auth

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Timestamp

class ServiceAuth(private val auth: FirebaseAuth, private val database: FirebaseFirestore) :
    ServiceAuthContract {


    override suspend fun signUp(
        name: String,
        birthday: Timestamp,
        email: String,
        password: String,
        success: (FirebaseUser?) -> Unit,
        error: (Throwable?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun signIn(
        email: String,
        password: String,
        success: (FirebaseUser?) -> Unit,
        error: (Throwable?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
    }

    override fun checkUserLogged(
        success: (FirebaseUser?) -> Unit,
        error: () -> Unit
    ) {
        if (auth.currentUser != null) {
            success(auth.currentUser)
        } else {
            error()
        }
    }

    override suspend fun signOut() {
        auth.signOut()
        database.clearPersistence()
    }

    override suspend fun updatePassword(
        pwdActual: String,
        pwd: String,
        success: () -> Unit,
        error: (Throwable?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(auth.currentUser?.email!!, pwdActual)
    }

}
