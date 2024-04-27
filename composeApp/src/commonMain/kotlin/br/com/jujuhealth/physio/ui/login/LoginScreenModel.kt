package br.com.jujuhealth.physio.ui.login

import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.domain.MessageModel
import br.com.jujuhealth.physio.data.domain.ViewModelState
import br.com.jujuhealth.physio.data.use_case.SignInUseCase
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.icerock.moko.resources.StringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenModel(private val signInUseCase: SignInUseCase) : ScreenModel {

    private val _loginStateResult: MutableStateFlow<ViewModelState<*>> =
        MutableStateFlow(ViewModelState.Default)
    val loginStateResult: StateFlow<ViewModelState<*>> =
        _loginStateResult

    fun singIn(email: String, password: String){
        screenModelScope.launch {
            signInUseCase.run(email,password, {
                handleSuccess()
            }, { stringRes ->
                handleError(stringRes)
            })
        }
    }

    private fun handleError(stringRes: StringResource? = null) {
        stringRes?.let { res ->
            _loginStateResult.update { ViewModelState.Error(MessageModel(res)) }
        } ?: run {
            _loginStateResult.update { ViewModelState.Error(MessageModel(MR.strings.general_error_message)) }
        }
    }

    private fun handleSuccess() {
        _loginStateResult.update { ViewModelState.Success(data = true) }
    }

}