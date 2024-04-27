package br.com.jujuhealth.physio.ui.add_patient

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.ErrorModel
import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.ViewModelState
import br.com.jujuhealth.physio.data.use_case.AddPatientUseCase
import br.com.jujuhealth.physio.data.use_case.PutPatientDiaryFeedbackUseCase
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddPatientScreenModel(
    private val addPatientUseCase: AddPatientUseCase
) : ScreenModel {

    private val _addModelState: MutableStateFlow<ViewModelState<*>> =
        MutableStateFlow(ViewModelState.Default)
    val addModelState: StateFlow<ViewModelState<*>> =
        _addModelState

    fun addPatient(patient: Patient, pwd: String, confirmPwd: String) {
        _addModelState.update { ViewModelState.Loading(true) }
        screenModelScope.launch {
            addPatientUseCase.run(
                patient = patient,
                pwd = pwd,
                confirmPwd = confirmPwd,
                {
                    handleSuccess(it)
                }, {
                    handleError(it)
                }
            )
        }
    }

    private fun handleSuccess(patient: Patient) = _addModelState.update { ViewModelState.Success(patient) }
    private fun handleError(stringRes: StringResource? = null) {
        stringRes?.let { res ->
            _addModelState.update { ViewModelState.Error(ErrorModel(res)) }
        } ?: run {
            _addModelState.update { ViewModelState.Error(ErrorModel(MR.strings.general_error_message)) }
        }
    }

}