package br.com.jujuhealth.physio.ui.auth

import androidx.compose.runtime.Composable
import br.com.jujuhealth.physio.data.request.auth.ServiceAuthContract
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dev.gitlive.firebase.auth.FirebaseUser
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthScreenModel(private val serviceAuth: ServiceAuthContract) : ScreenModel {

    private val _loginState: MutableStateFlow<ViewModelState<*>> =
        MutableStateFlow(ViewModelState.Default)
    val loginState: StateFlow<ViewModelState<*>> =
        _loginState

    fun singIn(email: String, password: String){
        screenModelScope.launch {
            serviceAuth.signIn("","", { user ->

            }, {

            })
        }
    }

    private fun handleError(throwable: Throwable) {
        // handle error
    }

    private fun handleSuccess(firebaseUser: FirebaseUser?) {
        firebaseUser?.let {
            _loginState.update { ViewModelState.Success(data = it) }
        } ?: run {
            handleError(Throwable())
        }
    }

}


sealed class ViewModelState<out T> {
    data class Error<T>(val error: ErrorModel) : ViewModelState<T>()
    data class Success<T>(val data: T) : ViewModelState<T>()
    data class Loading(val isLoading: Boolean = false) : ViewModelState<Unit>()
    data object Default : ViewModelState<Unit>()

}

class ErrorModel(
    private val messageRes: StringResource
){
    @Composable
    fun getErrorMessage(): String { return stringResource(messageRes) }
}