package br.com.jujuhealth.physio.data.model

import dev.gitlive.firebase.auth.FirebaseUser

data class User(
    val name: String? = "",
    val uId: String? = "",
    val email: String? = "",
    val providerId: String? = "",
) {

    companion object{
        fun buildUser(currentUser: FirebaseUser?): User {
            return User(
                name = "",
                uId = currentUser?.uid,
                email = currentUser?.email,
                providerId = currentUser?.providerId
            )
        }
    }

}