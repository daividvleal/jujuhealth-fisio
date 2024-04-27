package br.com.jujuhealth.physio.data.request.auth

import br.com.jujuhealth.physio.data.domain.Patient
import br.com.jujuhealth.physio.data.domain.User

interface ServiceAuthContract {

    suspend fun signIn(
        email: String,
        password: String,
        success: () -> Unit,
        error: () -> Unit
    )

    suspend fun signOut(error: () -> Unit)

    suspend fun updateUser(
        user: User,
        success: (user: User) -> Unit,
        error: () -> Unit
    )

    suspend fun getUser(success: (User?) -> Unit, error: () -> Unit)

    suspend fun createPatient(
        name: String,
        email: String,
        password: String,
        userPwd: String,
        success: (Patient, User) -> Unit,
        error: () -> Unit
    )

}