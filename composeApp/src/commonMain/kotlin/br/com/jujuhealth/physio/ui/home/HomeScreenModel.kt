package br.com.jujuhealth.physio.ui.home

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.ErrorModel
import br.com.jujuhealth.physio.data.model.User
import br.com.jujuhealth.physio.data.model.ViewModelState
import br.com.jujuhealth.physio.data.use_case.GetUserUseCase
import br.com.jujuhealth.physio.data.use_case.LoadPatientsUseCase
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.icerock.moko.resources.StringResource
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

    fun getUser() {
        _userState.update { ViewModelState.Loading(true) }
        screenModelScope.launch {
            getUserUseCase.run(
                success = {
                    handleSuccess(it)
                }, error = {
                    handleError(it)
                }
            )
        }
    }

    private fun handleError(stringRes: StringResource? = null) {
        stringRes?.let { res ->
            _userState.update { ViewModelState.Error(ErrorModel(res)) }
        } ?: run {
            _userState.update { ViewModelState.Error(ErrorModel(MR.strings.general_error_message)) }
        }
    }

    private fun handleSuccess(user: User) {
        screenModelScope.launch {
            loadPatientsUseCase.run(
                patientIds = (user.patients as? ArrayList<String>) ?: arrayListOf(),
                success = {
                    user.mutablePatientList = it
                    _userState.update { ViewModelState.Success(data = user) }
                },
                error = {
                    handleError(it)
                }
            )
        }
    }

}