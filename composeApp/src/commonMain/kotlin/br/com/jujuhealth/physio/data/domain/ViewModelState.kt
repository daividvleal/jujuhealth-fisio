package br.com.jujuhealth.physio.data.domain

sealed class ViewModelState<out T> {
    data class Error<T>(val error: T) : ViewModelState<T>()
    data class Success<T>(val data: T) : ViewModelState<T>()
    data object Loading : ViewModelState<Unit>()
    data object Default : ViewModelState<Unit>()
}