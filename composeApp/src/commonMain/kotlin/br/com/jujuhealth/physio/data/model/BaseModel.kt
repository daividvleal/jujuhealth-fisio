package br.com.jujuhealth.physio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseModel<T, E>(
    var status: Status,
    var data: T? = null,
    var error: E? = null
) {
    @Serializable
    enum class Status {
        DEFAULT, LOADING, SUCCESS, ERROR
    }
}