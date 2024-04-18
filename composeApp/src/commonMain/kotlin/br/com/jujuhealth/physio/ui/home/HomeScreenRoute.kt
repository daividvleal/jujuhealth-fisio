package br.com.jujuhealth.physio.ui.home

import CreateGenericError
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.ErrorModel
import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.User
import br.com.jujuhealth.physio.data.model.ViewModelState
import br.com.jujuhealth.physio.ui.addPatient.AddPatientScreenRoute
import br.com.jujuhealth.physio.ui.details.patient.PatientDetailsScreenRoute
import br.com.jujuhealth.physio.ui.uikit.CreateGenericLoading
import br.com.jujuhealth.physio.ui.uikit.CreatePersonDetails
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

data object HomeScreenRoute : Screen {
    @Composable
    override fun Content() {
        val homeScreenModel: HomeScreenModel = getScreenModel()
        val userStateFlow by homeScreenModel.userState.collectAsState()

        when (userStateFlow) {
            is ViewModelState.Success -> {
                val user = (userStateFlow as ViewModelState.Success).data as User
                CreateHomeScreen(user = user)
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
    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigator.push(AddPatientScreenRoute)
                },
                modifier = Modifier.padding(16.dp)
                    .size(56.dp)
                    .absoluteOffset(x = 16.dp, y = 16.dp),
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(MR.strings.home_toolbar_name),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                contentColor = Color.White
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            CreatePersonDetails(
                personName = user.name,
                personEmail = user.email
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 24.sp,
                text = stringResource(MR.strings.patients)
            )
            user.mutablePatientList.takeIf { it.isNotEmpty() }?.let {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp)
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
}

@Composable
fun createPatientItem(
    modifier: Modifier = Modifier.fillMaxWidth(),
    patient: Patient
) {
    val navigator = LocalNavigator.currentOrThrow
    Card(
        modifier = modifier.padding(top = 8.dp),
        backgroundColor = MaterialTheme.colors.primaryVariant
    ) {
        Row(
            modifier = modifier.padding(24.dp)
                .clickable {
                    navigator.push(PatientDetailsScreenRoute(patient))
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = patient.name.orEmpty(), fontSize = 16.sp, color = Color.White)
                Text(text = patient.email.orEmpty(), fontSize = 16.sp, color = Color.White)
            }
            Image(
                modifier = Modifier.clickable {
                    navigator.push(PatientDetailsScreenRoute(patient))
                },
                painter = painterResource(MR.images.ic_edit),
                contentDescription = "Edit Patient Details"
            )
        }
    }
}
