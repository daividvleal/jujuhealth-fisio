package br.com.jujuhealth.physio.data.request.sign

import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.Timestamp

interface ServiceAuthContract {

    suspend fun signIn(
        email: String,
        password: String,
        success: (FirebaseUser?) -> Unit,
        error: (Throwable?) -> Unit
    )

    suspend fun signUp(
        name: String,
        birthday: Timestamp,
        email: String,
        password: String,
        success: (FirebaseUser?) -> Unit,
        error: (Throwable?) -> Unit
    )

    suspend fun signOut()

    fun checkUserLogged(
        success: (FirebaseUser?) -> Unit,
        error: () -> Unit
    )

    suspend fun updatePassword(
        pwdActual: String,
        pwd: String,
        success: () -> Unit,
        error: (Throwable?) -> Unit
    )

}