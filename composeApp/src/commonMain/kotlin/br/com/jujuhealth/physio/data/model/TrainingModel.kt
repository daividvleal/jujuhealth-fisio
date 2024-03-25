package br.com.jujuhealth.physio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TrainingModel (
    val mode: Mode = Mode.SLOW,
    val difficulty: Difficulty = Difficulty.EASY,
    val repetitions: Int = 2,
    val contractionDuration: Long = 4000,
    val relaxDuration: Long = 4000
) {
    @Serializable
    enum class Mode {
        SLOW, FAST
    }

    @Serializable
    enum class Difficulty {
        EASY, MEDIUM, HARD
    }

}