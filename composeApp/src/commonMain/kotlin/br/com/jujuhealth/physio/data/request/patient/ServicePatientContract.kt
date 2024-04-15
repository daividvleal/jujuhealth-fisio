package br.com.jujuhealth.physio.data.request.patient

import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.TrainingDiary

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

}