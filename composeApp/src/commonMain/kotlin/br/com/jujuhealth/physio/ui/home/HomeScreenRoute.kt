package br.com.jujuhealth.physio.ui.home

import CreateMessage
import MessageType
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.domain.MessageModel
import br.com.jujuhealth.physio.data.domain.Patient
import br.com.jujuhealth.physio.data.domain.User
import br.com.jujuhealth.physio.data.domain.ViewModelState
import br.com.jujuhealth.physio.ui.add_patient.AddPatientScreenRoute
import br.com.jujuhealth.physio.ui.details.patient.PatientDetailsScreenRoute
import br.com.jujuhealth.physio.ui.uikit.CreateGenericLoading
import br.com.jujuhealth.physio.ui.uikit.CreatePersonDetails
import br.com.jujuhealth.physio.ui.uikit.CreateTopBar
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.colorResource
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
                CreateHomeScreen(user = user, homeScreenModel = homeScreenModel)
            }

            is ViewModelState.Error -> {
                val messageModel = (userStateFlow as ViewModelState.Error).error as MessageModel
                CreateMessage(messageModel, MessageType.ERROR)
            }

            is ViewModelState.Loading -> {
                CreateGenericLoading(colorResource(MR.colors.colorPrimaryDark))
            }

            ViewModelState.Default -> Unit
        }

        LifecycleEffect(onStarted =  {
            homeScreenModel.getUser()
        })
    }
}

@Composable
fun CreateHomeScreen(
    user: User,
    homeScreenModel: HomeScreenModel,
) {
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(
        backgroundColor = colorResource(MR.colors.colorPrimary),
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.primaryVariant,
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
            CreateTopBar(
                title = stringResource(MR.strings.home_toolbar_name),
                icon =  Icons.AutoMirrored.Filled.List) {
                navigator.pop()
            }
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
            CreatePatientsList(user, homeScreenModel)
        }
    }
}

@Composable
fun CreatePatientsList(user: User, homeScreenModel: HomeScreenModel) {
    var patientList by remember { mutableStateOf(Any()) }
    val patientsState by homeScreenModel.patientsState.collectAsState()

    when (patientsState) {
        is ViewModelState.Success<*> -> {
            (patientsState as? ViewModelState.Success)?.data?.let {
                patientList = it
            }
            (patientList as ArrayList<*>).takeIf { it.isNotEmpty() }
                ?.let { it ->
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp)
                    ) {
                        it.forEach { patient ->
                            item {
                                createPatientItem(patient = patient as Patient)
                            }
                        }
                    }
                }
        }

        is ViewModelState.Default -> homeScreenModel.loadPatients(user)
        is ViewModelState.Error -> CreateMessage(
            MessageModel(MR.strings.general_empty_message),
            MessageType.ERROR
        )

        is ViewModelState.Loading -> CreateGenericLoading(colorResource(MR.colors.colorPrimaryDark))
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
