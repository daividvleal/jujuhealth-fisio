package br.com.jujuhealth.physio.data.use_case

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.domain.Patient
import br.com.jujuhealth.physio.data.request.patient.ServicePatientContract
import dev.icerock.moko.resources.StringResource

class LoadPatientsUseCase(
    private val servicePatientContract: ServicePatientContract
) {
    suspend fun run(patientIds: ArrayList<String>, success: (ArrayList<Patient>) -> Unit, error: (errorMessage: StringResource?) -> Unit) {
        servicePatientContract.loadPatients(
            patientIds = patientIds,
            success = { patients ->
                patients.takeIf { patients.isNotEmpty() }?.let {
                    it.reverse()
                    success(it)
                } ?: run {
                    error.invoke(MR.strings.general_empty_message)
                }
        }, {
            error.invoke(MR.strings.general_empty_message)
        })
    }

}