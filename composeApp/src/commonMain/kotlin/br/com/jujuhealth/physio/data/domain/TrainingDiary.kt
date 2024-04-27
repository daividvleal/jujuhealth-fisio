package br.com.jujuhealth.physio.data.domain

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class TrainingDiary(
    var date: Timestamp = Timestamp.now(),
    var formattedDate: String = "00-00-00",
    var seriesSlowEasy: Int = 0,
    var seriesSlowMedium: Int = 0,
    var seriesSlowHard: Int = 0,
    var seriesFastEasy: Int = 0,
    var seriesFastMedium: Int = 0,
    var seriesFastHard: Int = 0,
    var urineLoss: ArrayList<Int> = arrayListOf(),
    val feedbacks: ArrayList<String> = arrayListOf()
) {

    fun totalExerciseAmount() = seriesSlowEasy + seriesSlowMedium + seriesSlowHard + seriesFastEasy + seriesFastMedium + seriesFastHard

    fun hasUrineLoss() = urineLoss.size > 0

    fun urineLossSize() = urineLoss.size

}