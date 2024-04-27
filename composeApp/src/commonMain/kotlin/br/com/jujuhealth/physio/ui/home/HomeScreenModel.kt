package br.com.jujuhealth.physio.ui.home

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.domain.MessageModel
import br.com.jujuhealth.physio.data.domain.User
import br.com.jujuhealth.physio.data.domain.ViewModelState
import br.com.jujuhealth.physio.data.use_case.GetUserUseCase
import br.com.jujuhealth.physio.data.use_case.LoadPatientsUseCase
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenModel(
    private val getUserUseCase: GetUserUseCase,
    private val loadPatientsUseCase: LoadPatientsUseCase
) : ScreenModel {

    private val _userState: MutableStateFlow<ViewModelState<*>> =
        MutableStateFlow(ViewModelState.Default)
    val userState: StateFlow<ViewModelState<*>> =
        _userState

    private val _patientsState: MutableStateFlow<ViewModelState<*>> =
        MutableStateFlow(ViewModelState.Default)
    val patientsState: StateFlow<ViewModelState<*>> =
        _patientsState

    fun getUser() {
        _userState.update { ViewModelState.Loading }
        screenModelScope.launch {
            getUserUseCase.run(
                success = {
                    handleSuccess(it)
                }, error = {
                    handleError(_userState, it)
                }
            )
        }
    }

    private fun handleError(stateFlow: MutableStateFlow<ViewModelState<*>>, stringRes: StringResource? = null) {
        stringRes?.let { res ->
            stateFlow.update { ViewModelState.Error(MessageModel(res)) }
        } ?: run {
            stateFlow.update { ViewModelState.Error(MessageModel(MR.strings.general_error_message)) }
        }
    }

    private fun handleSuccess(user: User) {
        _userState.update { ViewModelState.Success(data = user) }
    }

    fun loadPatients(user: User) {
        _patientsState.update { ViewModelState.Loading }
        screenModelScope.launch(Dispatchers.IO) {
            loadPatientsUseCase.run(
                patientIds = (user.patients as? ArrayList<String>) ?: arrayListOf(),
                success = {
                    _patientsState.value = ViewModelState.Success(data = it)
                },
                error = {
                    handleError(_patientsState, it)
                }
            )
        }
    }

}