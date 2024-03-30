package br.com.jujuhealth.physio.ui.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.ui.auth.AuthScreenModel
import br.com.jujuhealth.physio.ui.auth.ViewModelState
import br.com.jujuhealth.physio.ui.home.HomeScreenRoute
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.gitlive.firebase.auth.FirebaseUser
import dev.icerock.moko.resources.compose.stringResource

data object LoginScreenRoute : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val authScreenModel: AuthScreenModel = getScreenModel()

        val loginResult by authScreenModel.loginState.collectAsState()
        var buttonLoading by rememberSaveable{ mutableStateOf(false) }

        when (loginResult) {
            is ViewModelState.Error -> {
                buttonLoading = false
            }
            is ViewModelState.Loading -> {
                buttonLoading = true
            }
            is ViewModelState.Success -> {
                buttonLoading = true
                navigator.popUntilRoot()
                navigator.push(HomeScreenRoute)
            }

            ViewModelState.Default -> LoginScree(
                authScreenModel = authScreenModel
            )
        }

    }
}

@Composable
fun LoginScree(
    authScreenModel: AuthScreenModel
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(MR.strings.app_name)
            )
        }

        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text(stringResource(MR.strings.email))
                    }
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { password = it },
                    placeholder = {
                        Text(stringResource(MR.strings.password))
                    }
                )
                Button(
                    onClick = {
                        authScreenModel.singIn(
                            email = email,
                            password = password
                        )
                    }, content = {
                        Text(stringResource(MR.strings.login))
                    })
            }
        }

        Row(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(MR.strings.welcome_message)
            )
        }

    }
}