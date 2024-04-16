package br.com.jujuhealth.physio.data.request.patient

import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.TrainingDiary
import br.com.jujuhealth.physio.data.request.COLLECTION_DIARY
import br.com.jujuhealth.physio.data.request.COLLECTION_TRAINING_DIARY
import br.com.jujuhealth.physio.data.request.COLLECTION_USERS
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.FilterBuilder
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where

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

    override suspend fun loadPatientDiary(
        patientId: String,
        success: (ArrayList<TrainingDiary>) -> Unit,
        error: () -> Unit
    ) {
        try {
            database.collection(COLLECTION_TRAINING_DIARY).document(patientId)
                .collection(COLLECTION_DIARY).get().runCatching {
                val trainingDiary = arrayListOf<TrainingDiary>()
                this.documents.forEach {
                    trainingDiary.add(it.data())
                }
                success(trainingDiary)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error.invoke()
        }
    }

    override suspend fun loadPatientDiaryRange(
        startDate: String,
        endDate: String,
        patientId: String,
        success: (ArrayList<TrainingDiary>) -> Unit,
        error: () -> Unit
    ) {
        try {
            database.collection(COLLECTION_TRAINING_DIARY).document(patientId)
                .collection(COLLECTION_DIARY).where {
                FieldPath().documentId greaterThan startDate
                FieldPath().documentId lessThan endDate
            }.get().runCatching {
                val trainingDiary = arrayListOf<TrainingDiary>()
                this.documents.forEach {
                    trainingDiary.add(it.data())
                }
                success(trainingDiary)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error.invoke()
        }
    }


}