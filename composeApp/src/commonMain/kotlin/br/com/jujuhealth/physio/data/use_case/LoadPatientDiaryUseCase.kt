package br.com.jujuhealth.physio.data.use_case

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.domain.TrainingDiary
import br.com.jujuhealth.physio.data.request.patient.ServicePatientContract
import dev.icerock.moko.resources.StringResource

class LoadPatientDiaryUseCase(
    private val servicePatientContract: ServicePatientContract
) {

    suspend fun run(patientId: String?, success: (ArrayList<TrainingDiary>) -> Unit, error: (errorMessage: StringResource?) -> Unit) {
        servicePatientContract.loadPatientDiary(
            patientId = patientId.orEmpty(),
            success = { patients ->
                patients.takeIf { patients.isNotEmpty() }?.let {
                    it.reverse()
                    success(it)
                } ?: run {
                    error.invoke(MR.strings.general_training_empty_message)
                }
            }, {
                error.invoke(MR.strings.general_error_message)
            })
    }

}