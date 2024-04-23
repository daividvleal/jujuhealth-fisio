package br.com.jujuhealth.physio.ui.details.training

import androidx.compose.ui.hapticfeedback.HapticFeedback
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.ErrorModel
import br.com.jujuhealth.physio.data.model.TrainingDiary
import br.com.jujuhealth.physio.data.model.ViewModelState
import br.com.jujuhealth.physio.data.use_case.PutPatientDiaryFeedbackUseCase
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrainingDetailsScreenModel(
    private val putPatientDiaryFeedbackUseCase: PutPatientDiaryFeedbackUseCase
): ScreenModel {

    private val _trainingDiary: MutableStateFlow<ViewModelState<*>> =
        MutableStateFlow(ViewModelState.Default)
    val trainingDiary: StateFlow<ViewModelState<*>> =
        _trainingDiary

    fun addFeedBack(patientId: String?, trainingDiary: TrainingDiary, newFeedback: String) {
        _trainingDiary.update { ViewModelState.Loading(true) }
        screenModelScope.launch {
            putPatientDiaryFeedbackUseCase.run(
                patientId = patientId,
                trainingDiary = trainingDiary,
                newFeedback = newFeedback,
                success = {
                    handleSuccess(it)
                }, error = {
                    handleError(it)
                }
            )
        }
    }

    private fun handleSuccess(trainingDiary: TrainingDiary) {
        _trainingDiary.update { ViewModelState.Success(data = trainingDiary) }
    }

    private fun handleError(stringRes: StringResource? = null) {
        stringRes?.let { res ->
            _trainingDiary.update { ViewModelState.Error(ErrorModel(res)) }
        } ?: run {
            _trainingDiary.update { ViewModelState.Error(ErrorModel(MR.strings.general_error_message)) }
        }
    }
}