package br.com.jujuhealth.physio.ui.add_patient

import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.ViewModelState
import br.com.jujuhealth.physio.data.use_case.AddPatientUseCase
import br.com.jujuhealth.physio.data.use_case.PutPatientDiaryFeedbackUseCase
import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AddPatientScreenModel(
    private val addPatientUseCase: AddPatientUseCase
) : ScreenModel {

    private val _isFieldsValid: MutableStateFlow<Boolean> =
        MutableStateFlow(true)
    val isFieldsValid: StateFlow<Boolean> =
        _isFieldsValid

    private val _addModelState: MutableStateFlow<ViewModelState<*>> =
        MutableStateFlow(ViewModelState.Default)
    val addModelState: StateFlow<ViewModelState<*>> =
        _addModelState

    fun addPatient(patient: Patient, pwd: String, confirmPwd: String) {
        _isFieldsValid.update { true }
        if (addPatientUseCase.validateField(patient, pwd, confirmPwd)) {
            addPatientUseCase.run(
                patient,
                pwd,
                {}, {}
            )
        } else {
            _isFieldsValid.update { false }
        }
    }

    fun handleSuccess() = Unit
    fun handleError() = Unit

}