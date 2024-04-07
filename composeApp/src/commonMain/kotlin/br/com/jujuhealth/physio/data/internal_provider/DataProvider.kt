package br.com.jujuhealth.physio.data.internal_provider

import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.User

data object DataProvider {

    private var user: User? = null
    private val patients: List<Patient> = mutableListOf()

    fun buildUser(user: User) {

    }

}