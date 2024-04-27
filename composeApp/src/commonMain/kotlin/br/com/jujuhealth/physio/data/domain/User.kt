package br.com.jujuhealth.physio.data.domain

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String? = "",
    var uId: String? = "",
    val email: String? = "",
    val providerId: String? = "",
    val patients: MutableList<String> = mutableListOf()
)