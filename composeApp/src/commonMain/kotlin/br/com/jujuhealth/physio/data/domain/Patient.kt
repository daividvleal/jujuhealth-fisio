package br.com.jujuhealth.physio.data.domain

import kotlinx.serialization.Serializable

@Serializable
data class Patient(
    val name: String? = "",
    var uId: String? = "",
    val email: String? = "",
    var providerId: String? = ""
)