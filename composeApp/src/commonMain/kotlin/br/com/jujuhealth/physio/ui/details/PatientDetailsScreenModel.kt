package br.com.jujuhealth.physio.ui.details

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.ErrorModel
import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.TrainingDiary
import br.com.jujuhealth.physio.data.model.ViewModelState
import br.com.jujuhealth.physio.data.use_case.LoadPatientDiaryUseCase
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PatientDetailsScreenModel(
    private val loadPatientDiaryUseCase: LoadPatientDiaryUseCase
): ScreenModel {

    private val _patientDiaryList: MutableStateFlow<ViewModelState<*>> =
        MutableStateFlow(ViewModelState.Default)
    val patientDiaryList: StateFlow<ViewModelState<*>> =
        _patientDiaryList

    fun loadPatientDiary(patient: Patient) {
        _patientDiaryList.update { ViewModelState.Loading(true) }
        screenModelScope.launch {
            loadPatientDiaryUseCase.run(
                patientId = patient.uId,
                success = {
                    handlePatientDiaryListSuccess(it)
                }, error = {
                    handlePatientDiaryListSuccessError(it)
                }
            )
        }
    }

    private fun handlePatientDiaryListSuccess(patientDiaryList: ArrayList<TrainingDiary>) {
        _patientDiaryList.update { ViewModelState.Success(data = patientDiaryList) }
    }

    private fun handlePatientDiaryListSuccessError(stringRes: StringResource? = null) {
        stringRes?.let { res ->
            _patientDiaryList.update { ViewModelState.Error(ErrorModel(res)) }
        } ?: run {
            _patientDiaryList.update { ViewModelState.Error(ErrorModel(MR.strings.general_error_message)) }
        }
    }

}