package br.com.jujuhealth.physio.data.use_case

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.User
import br.com.jujuhealth.physio.data.request.auth.ServiceAuthContract
import dev.icerock.moko.resources.StringResource

class GetUserUseCase(private val serviceAuth: ServiceAuthContract) {

    suspend fun run( success: (User) -> Unit, error: (errorMessage: StringResource?) -> Unit) {
        serviceAuth.getUser({
            it?.let { success(it) } ?: run { error.invoke(MR.strings.general_error_message) }
        }, { error.invoke(MR.strings.general_error_message) })
    }

}