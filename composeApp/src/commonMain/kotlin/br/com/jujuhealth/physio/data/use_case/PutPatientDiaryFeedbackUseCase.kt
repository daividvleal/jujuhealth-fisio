package br.com.jujuhealth.physio.data.use_case

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.TrainingDiary
import br.com.jujuhealth.physio.data.request.patient.ServicePatientContract
import dev.icerock.moko.resources.StringResource

class PutPatientDiaryFeedbackUseCase(
    private val servicePatientContract: ServicePatientContract
) {
    suspend fun run(
        patientId: String?,
        trainingDiary: TrainingDiary,
        newFeedback: String?,
        success: (TrainingDiary) -> Unit, error: (errorMessage: StringResource?) -> Unit
    ) {
        patientId?.let {
            newFeedback?.takeIf { it.isNotEmpty() }?.let {
                trainingDiary.feedbacks.add(newFeedback)
                servicePatientContract.updatePatientDiary(
                    patientId = patientId,
                    trainingDiary = trainingDiary,
                    success = { patient ->
                        success(patient)
                    },
                    error = {
                        error.invoke(MR.strings.general_empty_message)
                    })
            } ?: run {
                error.invoke(MR.strings.general_empty_message)
            }
        }
    }

}