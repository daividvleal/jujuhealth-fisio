package br.com.jujuhealth.physio.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.ErrorModel
import br.com.jujuhealth.physio.data.model.ViewModelState
import br.com.jujuhealth.physio.ui.home.HomeScreenRoute
import br.com.jujuhealth.physio.ui.uikit.TextField
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.stringResource

data object LoginScreenRoute : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val loginScreenModel: LoginScreenModel = getScreenModel()

        val loginStateFlow by loginScreenModel.loginStateResult.collectAsState()
        var buttonLoading by rememberSaveable { mutableStateOf(false) }
        var errorMessage by rememberSaveable { mutableStateOf("") }

        when (loginStateFlow) {
            is ViewModelState.Error -> {
                buttonLoading = false
                errorMessage =
                    ((loginStateFlow as ViewModelState.Error<*>).error as? ErrorModel)?.getErrorMessage()
                        .orEmpty()
                LoginScree(
                    loginScreenModel = loginScreenModel,
                    loading = buttonLoading,
                    errorMessage = errorMessage
                )
            }

            is ViewModelState.Loading -> {
                buttonLoading = true
                errorMessage = String()
                LoginScree(
                    loginScreenModel = loginScreenModel,
                    loading = buttonLoading,
                    errorMessage = errorMessage
                )
            }

            is ViewModelState.Success -> {
                buttonLoading = true
                errorMessage = String()
                navigator.popUntilRoot()
                navigator.push(HomeScreenRoute)
            }

            ViewModelState.Default -> {
                LoginScree(
                    loginScreenModel = loginScreenModel,
                    loading = buttonLoading,
                    errorMessage = errorMessage
                )
            }
        }

    }
}

@Composable
fun LoginScree(
    loginScreenModel: LoginScreenModel,
    loading: Boolean = false,
    errorMessage: String = String()
) {

    var email by rememberSaveable { mutableStateOf("daivid.v.leal@gmail.com") }
    var password by rememberSaveable { mutableStateOf("123456") }

    Column(
        modifier = Modifier.fillMaxSize().background(colorResource(MR.colors.colorPrimary)),
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
                color = Color.White,
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
                    text = email,
                    hint = stringResource(MR.strings.email),
                    onValueChange = {
                        email = it
                    }
                )
                TextField(
                    text = password,
                    hint = stringResource(MR.strings.password),
                    onValueChange = {
                        password = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                )
                Button(
                    onClick = {
                        loginScreenModel.singIn(
                            email = email,
                            password = password
                        )
                    }, content = {
                        Text(stringResource(MR.strings.login))
                    })
                if (loading) {
                    CircularProgressIndicator()
                }
                Text(
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colors.secondaryVariant,
                    text = errorMessage
                )
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
                color = Color.White,
                text = stringResource(MR.strings.welcome_message)
            )
        }

    }
}