package br.com.jujuhealth.physio.data.request.patient

import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.request.COLLECTION_USERS
import dev.gitlive.firebase.firestore.FirebaseFirestore

class ServicePatientImpl(
    private val database: FirebaseFirestore
) : ServicePatientContract {
    override suspend fun loadPatients(
        patientIds: ArrayList<String>,
        success: (ArrayList<Patient>) -> Unit,
        error: () -> Unit
    ) {
        val patients: ArrayList<Patient> = arrayListOf()
        var hasException = false
        patientIds.forEach {
            try {
                database.collection(COLLECTION_USERS).document(it).get().runCatching {
                    val patient = data<Patient>()
                    patient.uId = it
                    patients.add(patient)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                hasException = true
            }
        }

        if (patients.isEmpty() && hasException) {
            error.invoke()
        } else {
            success.invoke(patients)
        }

    }


}