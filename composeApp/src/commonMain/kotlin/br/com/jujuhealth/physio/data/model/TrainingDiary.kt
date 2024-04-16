package br.com.jujuhealth.physio.data.model

import dev.gitlive.firebase.firestore.Timestamp
import io.ktor.util.date.GMTDate
import kotlinx.serialization.Serializable

@Serializable
data class TrainingDiary(
    var date: Timestamp = Timestamp.now(),
    var seriesSlowEasy: Int = 0,
    var seriesSlowMedium: Int = 0,
    var seriesSlowHard: Int = 0,
    var seriesFastEasy: Int = 0,
    var seriesFastMedium: Int = 0,
    var seriesFastHard: Int = 0,
    var urineLoss: ArrayList<Int> = ArrayList()
) {

    fun getFormattedDate(): String {
        val gmtDate = GMTDate(date.seconds)
        return "${gmtDate.dayOfMonth}/${gmtDate.month}/${gmtDate.year}"
    }

    fun totalExerciseAmount() = seriesSlowEasy + seriesSlowMedium + seriesSlowHard + seriesFastEasy + seriesFastMedium + seriesFastHard

    fun hasUrineLoss() = urineLoss.size > 0

    fun urineLossSize() = urineLoss.size

}