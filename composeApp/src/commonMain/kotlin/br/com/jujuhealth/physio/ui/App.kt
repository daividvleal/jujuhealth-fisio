package br.com.jujuhealth.physio.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.ui.details.patient.PatientDetailsScreenRoute
import br.com.jujuhealth.physio.ui.login.LoginScreenRoute
import cafe.adriel.voyager.navigator.Navigator
import dev.icerock.moko.resources.compose.colorResource

@Composable
fun App() {
    MaterialTheme(
        colors = lightColors(
            primary = colorResource(MR.colors.colorPrimary),
            primaryVariant = colorResource(MR.colors.colorPrimaryDark),
            secondary = colorResource(MR.colors.colorAccent),
            secondaryVariant = colorResource(MR.colors.colorAccentDark)
        )
    ) {
        Navigator(LoginScreenRoute)
    }
}

