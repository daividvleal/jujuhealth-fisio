package br.com.jujuhealth.physio.data.request.patient

import br.com.jujuhealth.physio.data.domain.Patient
import br.com.jujuhealth.physio.data.domain.TrainingDiary

interface ServicePatientContract {

    suspend fun loadPatients(
        patientIds: ArrayList<String>,
        success: (ArrayList<Patient>) -> Unit,
        error: () -> Unit
    )

    suspend fun loadPatientDiary(
        patientId: String,
        success: (ArrayList<TrainingDiary>) -> Unit,
        error: () -> Unit
    )

    suspend fun loadPatientDiaryRange(
        startDate: String, endDate: String,
        patientId: String,
        success: (ArrayList<TrainingDiary>) -> Unit,
        error: () -> Unit
    )

    suspend fun updatePatientDiary(
        patientId: String,
        trainingDiary: TrainingDiary,
        success: (TrainingDiary) -> Unit, error: () -> Unit
    )

}