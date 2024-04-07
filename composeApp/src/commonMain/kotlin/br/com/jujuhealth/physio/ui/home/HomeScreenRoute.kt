package br.com.jujuhealth.physio.ui.home

import CreateGenericError
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.ErrorModel
import br.com.jujuhealth.physio.data.model.User
import br.com.jujuhealth.physio.data.model.ViewModelState
import br.com.jujuhealth.physio.ui.common.CreateGenericLoading
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

data object HomeScreenRoute : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val homeScreenModel: HomeScreenModel = getScreenModel()

        val userStateFlow by homeScreenModel.userState.collectAsState()

        when (userStateFlow) {
            is ViewModelState.Success -> {
                val user = (userStateFlow as ViewModelState.Success).data as User
                CreateHomeScreen(user)
            }

            is ViewModelState.Error -> {
                CreateGenericError(ErrorModel(MR.strings.general_error_message))
            }

            is ViewModelState.Loading -> {
                CreateGenericLoading()
            }

            ViewModelState.Default -> {
                homeScreenModel.getUser()
            }
        }
    }
}

@Composable
fun CreateHomeScreen(
    user: User
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = user.name.orEmpty())
            Text(text = user.email.orEmpty())
            Text(text = user.uId.orEmpty())
        }

    }
}