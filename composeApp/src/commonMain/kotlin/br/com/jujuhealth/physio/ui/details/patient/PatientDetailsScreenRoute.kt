package br.com.jujuhealth.physio.ui.details.patient

import CreateMessage
import MessageType
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.domain.MessageModel
import br.com.jujuhealth.physio.data.domain.Patient
import br.com.jujuhealth.physio.data.domain.TrainingDiary
import br.com.jujuhealth.physio.data.domain.ViewModelState
import br.com.jujuhealth.physio.ui.details.training.TrainingDetailsScreenRoute
import br.com.jujuhealth.physio.ui.uikit.CreateGenericLoading
import br.com.jujuhealth.physio.ui.uikit.CreatePersonDetails
import br.com.jujuhealth.physio.ui.uikit.CreateTopBar
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class PatientDetailsScreenRoute(
    private val patient: Patient
) : Screen {

    @Composable
    override fun Content() {
        val patientDetailsScreenModel: PatientDetailsScreenModel = getScreenModel()
        val patientDiaryListStateFlow by patientDetailsScreenModel.patientDiaryList.collectAsState()

        val trainingDiaryListContent: @Composable ((PaddingValues) -> Unit) =
            when (patientDiaryListStateFlow) {
                is ViewModelState.Success -> { _ ->
                    val trainingDiaryList =
                        (patientDiaryListStateFlow as ViewModelState.Success).data as ArrayList<*>
                    createTrainingDiaryListContent(trainingDiaryList, patient)
                }

                is ViewModelState.Error -> { _ ->
                    val messageModel =
                        (patientDiaryListStateFlow as ViewModelState.Error).error as MessageModel
                    CreateMessage(messageModel, MessageType.ERROR)
                }

                is ViewModelState.Loading -> { _ ->
                    CreateGenericLoading()
                }

                is ViewModelState.Default -> { _ ->
                    patientDetailsScreenModel.loadPatientDiary(patient = patient)
                }
            }

        CreatePatientDetailsScreen(
            patient = patient,
            trainingDiaryListContent = trainingDiaryListContent
        )
    }
}


@Composable
fun CreatePatientDetailsScreen(
    patient: Patient,
    trainingDiaryListContent: @Composable ((PaddingValues) -> Unit)? = null
) {
    val navigator = LocalNavigator.current

    Scaffold(
        backgroundColor = colorResource(MR.colors.colorPrimary),
        topBar = {
            CreateTopBar(stringResource(MR.strings.patient_toolbar_name)) {
                navigator?.pop()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            CreatePersonDetails(
                personName = patient.name,
                personEmail = patient.email
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 24.sp,
                text = stringResource(MR.strings.patient_diary)
            )
            trainingDiaryListContent?.invoke(it)
        }
    }
}

@Composable
fun createTrainingDiaryListContent(trainingDiaryList: ArrayList<*>?, patient: Patient) {
    trainingDiaryList?.takeIf { it.isNotEmpty() }?.let {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            it.forEach { trainingDiary ->
                item {
                    createTrainingDiaryItem(trainingDiary = trainingDiary as TrainingDiary, patient = patient)
                }
            }
        }
    } ?: run { CreateMessage(MessageModel(MR.strings.general_training_empty_message), MessageType.ALERT) }
}

@Composable
fun createTrainingDiaryItem(
    modifier: Modifier = Modifier.fillMaxWidth(),
    trainingDiary: TrainingDiary,
    patient: Patient
) {
    val navigator = LocalNavigator.currentOrThrow
    Card(
        modifier = modifier.padding(top = 8.dp),
        backgroundColor = colorResource(MR.colors.colorPrimaryDark)
    ) {
        Row(
            modifier = modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(
                        MR.strings.date,
                        trainingDiary.formattedDate
                    ), style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = stringResource(
                        MR.strings.total_series_amount,
                        trainingDiary.totalExerciseAmount().toString()
                    ), fontSize = 16.sp, color = Color.White
                )
                Text(
                    text = stringResource(
                        MR.strings.total_loss_amount,
                        trainingDiary.urineLossSize().toString()
                    ), fontSize = 16.sp, color = Color.White
                )
            }
            Image(
                modifier = Modifier.clickable {
                    navigator.push(TrainingDetailsScreenRoute(trainingDiary = trainingDiary, patient = patient))
                },
                painter = painterResource(MR.images.ic_edit),
                contentDescription = "Edit Patient Details"
            )
        }
    }
}