package br.com.jujuhealth.physio.ui.home

import CreateGenericError
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.ErrorModel
import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.User
import br.com.jujuhealth.physio.data.model.ViewModelState
import br.com.jujuhealth.physio.ui.common.CreateGenericLoading
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource

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
                val errorModel = (userStateFlow as ViewModelState.Error).error as ErrorModel
                CreateGenericError(errorModel)
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
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // IMAGE
            Text(text = user.name.orEmpty())
            Text(text = user.email.orEmpty())
            Text(text = user.uId.orEmpty())
        }

        user.mutablePatientList.takeIf { it.isNotEmpty() }?.let {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                user.mutablePatientList.forEach {
                    item {
                        createPatientItem(patient = it)
                    }
                }
            }
        } ?: run {
            CreateGenericError(ErrorModel(MR.strings.general_empty_message))
        }


    }
}

@Composable
fun createPatientItem(modifier: Modifier = Modifier.fillMaxWidth(), patient: Patient) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = patient.name.orEmpty())
            Image(
                painter = painterResource(MR.images.ic_edit),
                contentDescription = "Edit Item"
            )
        }
    }

}