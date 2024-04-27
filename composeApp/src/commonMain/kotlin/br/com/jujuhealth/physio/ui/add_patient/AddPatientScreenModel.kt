package br.com.jujuhealth.physio.ui.add_patient

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.domain.MessageModel
import br.com.jujuhealth.physio.data.domain.Patient
import br.com.jujuhealth.physio.data.domain.ViewModelState
import br.com.jujuhealth.physio.data.use_case.AddPatientUseCase
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.icerock.moko.resources.StringResource
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

    fun addPatient(patient: Patient, pwd: String, confirmPwd: String, userPwd: String) {
        _addModelState.update { ViewModelState.Loading }
        screenModelScope.launch {
            addPatientUseCase.run(
                patientToAdd = patient,
                pwd = pwd,
                userPwd = userPwd,
                confirmPwd = confirmPwd,
                success = {
                    handleSuccess(it)
                }, error = {
                    handleError(it)
                }
            )
        }
    }

    private fun handleSuccess(patient: Patient) {
        _addModelState.update { ViewModelState.Success(patient) }

    }

    private fun handleError(stringRes: StringResource? = null) {
        stringRes?.let { res ->
            _addModelState.update { ViewModelState.Error(MessageModel(res)) }
        } ?: run {
            _addModelState.update { ViewModelState.Error(MessageModel(MR.strings.general_error_message)) }
        }
    }

}