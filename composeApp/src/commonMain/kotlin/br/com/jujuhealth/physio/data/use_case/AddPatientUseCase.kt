package br.com.jujuhealth.physio.data.use_case

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.User
import br.com.jujuhealth.physio.data.request.auth.ServiceAuthContract
import br.com.jujuhealth.physio.data.request.auth.ServiceAuthImpl
import br.com.jujuhealth.physio.data.request.patient.ServicePatientContract
import dev.icerock.moko.resources.StringResource

class AddPatientUseCase(
    private val serviceAuth: ServiceAuthContract
) {

    suspend fun run(
        patient: Patient?,
        pwd: String?,
        confirmPwd: String?,
        success: (Patient) -> Unit,
        error: (errorMessage: StringResource?) -> Unit
    ) {
        if (validateField(patient, pwd, confirmPwd)) {
            serviceAuth.signUp(
                name = patient?.name.orEmpty(),
                email = patient?.email.orEmpty(),
                password = pwd.orEmpty(),
                success = success::invoke,
                error = {
                    error.invoke(MR.strings.general_error_message)
                }
            )
        } else {
            error.invoke(MR.strings.general_error_message)
        }

    }

    fun validateField(
        patient: Patient?,
        pwd: String?,
        confirmPwd: String?
    ): Boolean =
        (pwd?.isEmpty() != false ||
                patient?.name?.isEmpty() != false ||
                patient.email?.isEmpty() != false ||
                pwd == confirmPwd).not()

}