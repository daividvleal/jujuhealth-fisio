package br.com.jujuhealth.physio.data.request.auth

import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.User
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.Timestamp

interface ServiceAuthContract {

    suspend fun signIn(
        email: String,
        password: String,
        success: () -> Unit,
        error: () -> Unit
    )

    suspend fun signOut(error: () -> Unit)

    suspend fun updatePassword(
        pwdActual: String,
        pwd: String,
        success: () -> Unit,
        error: () -> Unit
    )

    suspend fun getUser(success: (User?) -> Unit, error: () -> Unit)

    fun checkUserLogged(
        success: (FirebaseUser?) -> Unit,
        error: () -> Unit
    )

    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        success: (Patient) -> Unit,
        error: () -> Unit
    )

}