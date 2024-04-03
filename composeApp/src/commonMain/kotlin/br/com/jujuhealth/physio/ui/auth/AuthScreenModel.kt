package br.com.jujuhealth.physio.ui.auth

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.ErrorModel
import br.com.jujuhealth.physio.data.model.User
import br.com.jujuhealth.physio.data.model.ViewModelState
import br.com.jujuhealth.physio.data.use_case.ServiceAuthUseCase
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthScreenModel(private val serviceAuthUseCase: ServiceAuthUseCase) : ScreenModel {

    private val _loginStateResult: MutableStateFlow<ViewModelState<*>> =
        MutableStateFlow(ViewModelState.Default)
    val loginStateResult: StateFlow<ViewModelState<*>> =
        _loginStateResult

    fun singIn(email: String, password: String){
        screenModelScope.launch {
            serviceAuthUseCase.signIn(email,password, { user ->
                handleSuccess(user)
            }, { stringRes ->
                handleError(stringRes)
            })
        }
    }

    private fun handleError(stringRes: StringResource? = null) {
        stringRes?.let { res ->
            _loginStateResult.update { ViewModelState.Error(ErrorModel(res)) }
        } ?: run {
            _loginStateResult.update { ViewModelState.Error(ErrorModel(MR.strings.general_error_message)) }
        }
    }

    private fun handleSuccess(user: User?) {
        user?.let {
            _loginStateResult.update { ViewModelState.Success(data = it) }
        } ?: run {
            handleError()
        }
    }

}