package br.com.jujuhealth.physio.data.use_case

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.request.auth.ServiceAuthContract
import dev.icerock.moko.resources.StringResource

class SignInUseCase(private val serviceAuth: ServiceAuthContract) {

    suspend fun run(
        email: String?,
        password: String?,
        success: () -> Unit,
        error: (errorMessage: StringResource?) -> Unit
    ) {
        if (isEmailAndPasswordValid(email = email, password = password)) {
            error(MR.strings.email_password_error_message)
        } else {
            serviceAuth.signIn(
                email = email!!, password = password!!, success = {
                    success()
                }, error = {
                    error.invoke(null)
                })
        }
    }

    private fun isEmailAndPasswordValid(
        email: String?,
        password: String?
    ) = email.isNullOrEmpty() || password.isNullOrEmpty()

}