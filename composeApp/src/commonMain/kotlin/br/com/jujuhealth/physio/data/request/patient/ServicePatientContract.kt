package br.com.jujuhealth.physio.data.request.patient

import br.com.jujuhealth.physio.data.model.Patient

interface ServicePatientContract {

    suspend fun loadPatients(
        patientIds: ArrayList<String>,
        success: (ArrayList<Patient>) -> Unit,
        error: () -> Unit
    )

}