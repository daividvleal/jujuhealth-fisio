package br.com.jujuhealth.physio.data.request.patient

import br.com.jujuhealth.physio.data.domain.Patient
import br.com.jujuhealth.physio.data.domain.TrainingDiary
import br.com.jujuhealth.physio.data.request.COLLECTION_DIARY
import br.com.jujuhealth.physio.data.request.COLLECTION_TRAINING_DIARY
import br.com.jujuhealth.physio.data.request.COLLECTION_USERS
import dev.gitlive.firebase.firestore.FieldPath
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
                    val trainingDiaryList = arrayListOf<TrainingDiary>()
                    this.documents.forEach {
                        val trainingDiary = it.data<TrainingDiary>()
                        trainingDiary.formattedDate = it.id
                        trainingDiaryList.add(trainingDiary)
                    }
                    success(trainingDiaryList)
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
                    val trainingDiaryList = arrayListOf<TrainingDiary>()
                    this.documents.forEach {
                        val trainingDiary = it.data<TrainingDiary>()
                        trainingDiary.formattedDate = it.id
                        trainingDiaryList.add(trainingDiary)
                    }
                    success(trainingDiaryList)
                }
        } catch (e: Exception) {
            e.printStackTrace()
            error.invoke()
        }
    }

    override suspend fun updatePatientDiary(
        patientId: String,
        trainingDiary: TrainingDiary,
        success: (TrainingDiary) -> Unit, error: () -> Unit
    ) {
        try {
            database.collection(COLLECTION_TRAINING_DIARY).document(patientId)
                .collection(COLLECTION_DIARY).document(trainingDiary.formattedDate).set(
                    data = trainingDiary
                ).runCatching {
                    success(trainingDiary)
                }
        } catch (e: Exception) {
            e.printStackTrace()
            error.invoke()
        }
    }

}