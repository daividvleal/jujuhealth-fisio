package br.com.jujuhealth.physio.ui.details

import CreateGenericError
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.ErrorModel
import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.TrainingDiary
import br.com.jujuhealth.physio.data.model.User
import br.com.jujuhealth.physio.data.model.ViewModelState
import br.com.jujuhealth.physio.ui.home.CreateHomeScreen
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
    private val patient: Patient = Patient(
        uId = "T6mb3u5opQaCNdEv7n4KR78Fwmf1",
        name = "Daivid Vasconcelos Leal",
        providerId = "firebase",
        email = "daivid.v.leal@gmail.com"
    )
) : Screen {

    @Composable
    override fun Content() {
        val patientDetailsScreenModel: PatientDetailsScreenModel = getScreenModel()
        val patientDiaryListStateFlow by patientDetailsScreenModel.patientDiaryList.collectAsState()

        val trainingDiaryListContent: @Composable ((PaddingValues) -> Unit)? =
            when (patientDiaryListStateFlow) {
                is ViewModelState.Success -> { _ ->
                    val trainingDiaryList =
                        (patientDiaryListStateFlow as ViewModelState.Success).data as ArrayList<TrainingDiary>
                    createTrainingDiaryListContent(trainingDiaryList)
                }

                is ViewModelState.Error -> { _ ->
                    val errorModel =
                        (patientDiaryListStateFlow as ViewModelState.Error).error as ErrorModel
                    CreateGenericError(errorModel)
                }

                is ViewModelState.Loading -> { _ ->
                    CreateGenericLoading()
                }

                ViewModelState.Default -> { _ ->
                    patientDetailsScreenModel.loadPatientDiary(patient = patient)
                }
            }

        CreatePatientScreen(
            patient = patient,
            trainingDiaryListContent = trainingDiaryListContent
        )
    }
}


@Composable
fun CreatePatientScreen(
    patient: Patient,
    trainingDiaryListContent: @Composable ((PaddingValues) -> Unit)? = null
) {
    val navigator = LocalNavigator.current

    Scaffold(
        topBar = {
            CreateTopBar(stringResource(MR.strings.patient_toolbar_name)) {
                navigator?.popUntilRoot()
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
            trainingDiaryListContent?.invoke(it)
        }
    }
}

@Composable
fun createTrainingDiaryListContent(trainingDiaryList: ArrayList<TrainingDiary>?) {
    trainingDiaryList?.takeIf { it.isNotEmpty() }?.let {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            it.forEach { trainingDiary ->
                item {
                    createTrainingDiaryItem(trainingDiary = trainingDiary)
                }
            }
        }
    } ?: run { CreateGenericError(ErrorModel(MR.strings.general_empty_message)) }
}

@Composable
fun createTrainingDiaryItem(
    modifier: Modifier = Modifier.fillMaxWidth(),
    trainingDiary: TrainingDiary
) {
    Card(
        modifier = modifier.padding(top = 8.dp),
        backgroundColor = colorResource(MR.colors.softPink)
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
                        trainingDiary.getFormattedDate()
                    ), fontSize = 16.sp, color = Color.White
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
        }
    }
}

/*
* Text(
                    text = stringResource(
                        MR.strings.series_slow_easy,
                        trainingDiary.seriesSlowEasy.toString()
                    ), fontSize = 16.sp, color = Color.White
                )
                Text(
                    text = stringResource(
                        MR.strings.series_slow_medium,
                        trainingDiary.seriesSlowMedium.toString()
                    ), fontSize = 16.sp, color = Color.White
                )
                Text(
                    text = stringResource(
                        MR.strings.series_slow_hard,
                        trainingDiary.seriesSlowHard.toString()
                    ), fontSize = 16.sp, color = Color.White
                )
                Text(
                    text = stringResource(
                        MR.strings.series_fast_easy,
                        trainingDiary.seriesFastEasy.toString()
                    ), fontSize = 16.sp, color = Color.White
                )
                Text(
                    text = stringResource(
                        MR.strings.series_fast_medium,
                        trainingDiary.seriesFastMedium.toString()
                    ), fontSize = 16.sp, color = Color.White
                )
                Text(
                    text = stringResource(
                        MR.strings.series_fast_hard,
                        trainingDiary.seriesFastHard.toString()
                    ), fontSize = 16.sp, color = Color.White
                )*/
