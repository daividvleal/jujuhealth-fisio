package br.com.jujuhealth.physio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String? = "",
    var uId: String? = "",
    val email: String? = "",
    val providerId: String? = "",
    val patients: List<String> = mutableListOf()
) {

    var mutablePatientList = mutableListOf<Patient>()

}