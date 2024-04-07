package br.com.jujuhealth.physio.data.model

sealed class ViewModelState<out T> {
    data class Error<T>(val error: T) : ViewModelState<T>()
    data class Success<T>(val data: T) : ViewModelState<T>()
    data class Loading(val isLoading: Boolean = false) : ViewModelState<Unit>()
    data object Default : ViewModelState<Unit>()
}