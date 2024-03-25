package br.com.jujuhealth.physio.data.model

import dev.gitlive.firebase.firestore.Timestamp
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

    fun hasExercise(): Boolean {
        if (seriesSlowEasy + seriesSlowMedium + seriesSlowHard + seriesFastEasy + seriesFastMedium + seriesFastHard > 0) {
            return true
        }
        return false
    }

    fun returnSpecifySerieFromMode(trainingModel: TrainingModel?): Int{
        return trainingModel?.let {
            when (trainingModel.mode) {
                TrainingModel.Mode.SLOW -> {
                    when (trainingModel.difficulty) {
                        TrainingModel.Difficulty.EASY -> {
                            seriesSlowEasy
                        }
                        TrainingModel.Difficulty.MEDIUM -> {
                            seriesSlowMedium
                        }
                        TrainingModel.Difficulty.HARD -> {
                            seriesSlowHard
                        }
                    }
                }
                TrainingModel.Mode.FAST -> {
                    when (trainingModel.difficulty) {
                        TrainingModel.Difficulty.EASY -> {
                            seriesFastEasy
                        }
                        TrainingModel.Difficulty.MEDIUM -> {
                            seriesFastMedium
                        }
                        TrainingModel.Difficulty.HARD -> {
                            seriesFastHard
                        }
                    }
                }
            }
        } ?: run {
            0
        }
    }

    fun addTraining(trainingModel: TrainingModel?, qtd: Int): TrainingDiary {
        return trainingModel?.let {
            when (trainingModel.mode) {
                TrainingModel.Mode.SLOW -> {
                    when (trainingModel.difficulty) {
                        TrainingModel.Difficulty.EASY -> {
                            seriesSlowEasy = qtd
                        }
                        TrainingModel.Difficulty.MEDIUM -> {
                            seriesSlowMedium = qtd
                        }
                        TrainingModel.Difficulty.HARD -> {
                            seriesSlowHard = qtd
                        }
                    }
                }
                TrainingModel.Mode.FAST -> {
                    when (trainingModel.difficulty) {
                        TrainingModel.Difficulty.EASY -> {
                            seriesFastEasy = qtd
                        }
                        TrainingModel.Difficulty.MEDIUM -> {
                            seriesFastMedium = qtd
                        }
                        TrainingModel.Difficulty.HARD -> {
                            seriesFastHard = qtd
                        }
                    }
                }
            }
            this
        } ?: run {
            TrainingDiary()
        }
    }

    fun hasUrineLoss() = urineLoss.size > 0

    fun urineLossSize() = urineLoss.size

}