package br.com.jujuhealth.physio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Patient(
    val name: String? = "",
    var uId: String? = "",
    val email: String? = "",
    val providerId: String? = ""
)