package br.com.jujuhealth.physio.data.request.auth

import br.com.jujuhealth.physio.data.model.User

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

}