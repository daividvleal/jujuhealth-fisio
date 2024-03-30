package br.com.jujuhealth.physio.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import br.com.jujuhealth.physio.ui.auth.login.LoginScreenRoute
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun App() {
    MaterialTheme {
        Navigator(LoginScreenRoute)
    }
}