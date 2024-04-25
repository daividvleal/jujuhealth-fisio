package br.com.jujuhealth.physio.data.use_case

import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.User
import br.com.jujuhealth.physio.data.request.patient.ServicePatientContract
import dev.icerock.moko.resources.StringResource

class AddPatientUseCase(
    private val servicePatientContract: ServicePatientContract
) {

    fun run(
        patient: Patient,
        pwd: String,
        success: (Patient) -> Unit,
        error: (errorMessage: StringResource?) -> Unit
    ) {

    }

    fun validateField(
        patient: Patient,
        pwd: String,
        confirmPwd: String
    ): Boolean = true
}