package br.com.jujuhealth.physio.ui.details.training

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.domain.Patient
import br.com.jujuhealth.physio.data.domain.TrainingDiary
import br.com.jujuhealth.physio.data.domain.ViewModelState
import br.com.jujuhealth.physio.ui.uikit.CreatePersonDetails
import br.com.jujuhealth.physio.ui.uikit.CreateTopBar
import br.com.jujuhealth.physio.ui.uikit.TextField
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

class TrainingDetailsScreenRoute(
    private val trainingDiary: TrainingDiary,
    private val patient: Patient
) : Screen {

    @Composable
    override fun Content() {

        var trainingDiaryState = remember {
            trainingDiary
        }
        var errorState = remember {
            false
        }
        var loading = remember {
            false
        }

        val trainingDetailsScreenModel: TrainingDetailsScreenModel = getScreenModel()
        val trainingDiaryStateModel by trainingDetailsScreenModel.trainingDiary.collectAsState()

        when (trainingDiaryStateModel) {
            ViewModelState.Default -> Unit

            is ViewModelState.Error -> {
                errorState = true
                loading = false
            }

            is ViewModelState.Loading -> {
                loading = true
                errorState = false
            }

            is ViewModelState.Success -> {
                loading = false
                errorState = false
                val trainingDiary =
                    (trainingDiaryStateModel as ViewModelState.Success).data as TrainingDiary
                trainingDiaryState = trainingDiary
            }
        }

        CreateTrainingDetailsScreen(
            trainingDiary = trainingDiaryState,
            patient = patient,
            trainingDetailsScreenModel = trainingDetailsScreenModel,
            errorState = errorState,
            loading = loading
        )
    }

}

@Composable
fun CreateTrainingDetailsScreen(
    trainingDetailsScreenModel: TrainingDetailsScreenModel,
    trainingDiary: TrainingDiary,
    patient: Patient,
    errorState: Boolean,
    loading: Boolean
) {
    val navigator = LocalNavigator.current
    val coroutineScope = rememberCoroutineScope()
    val errorMessage = stringResource(MR.strings.general_error_message)

    LaunchedEffect(key1 = errorState) {
        coroutineScope.launch {
            SnackbarHostState().showSnackbar(errorMessage)
        }
    }

    Scaffold(
        backgroundColor = colorResource(MR.colors.colorPrimary),
        topBar = {
            CreateTopBar(stringResource(MR.strings.exercise_toolbar_name)) {
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
                text = trainingDiary.formattedDate
            )
            CreateTrainingDiaryDetails(
                patient = patient,
                trainingDetailsScreenModel = trainingDetailsScreenModel,
                trainingDiary = trainingDiary,
                loading = loading
            )
        }
    }
}

@Composable
fun CreateTrainingDiaryDetails(
    patient: Patient,
    trainingDetailsScreenModel: TrainingDetailsScreenModel,
    trainingDiary: TrainingDiary,
    loading: Boolean
) {
    var newFeedback by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        backgroundColor = MaterialTheme.colors.primaryVariant
    ) {
        Column(Modifier.fillMaxWidth().padding(8.dp)) {
            Text(
                text = stringResource(
                    MR.strings.series_slow_easy,
                    trainingDiary.seriesSlowEasy.toString()
                ), fontSize = 16.sp
            )
            Text(
                text = stringResource(
                    MR.strings.series_slow_medium,
                    trainingDiary.seriesSlowMedium.toString()
                ), fontSize = 16.sp
            )
            Text(
                text = stringResource(
                    MR.strings.series_slow_hard,
                    trainingDiary.seriesSlowHard.toString()
                ), fontSize = 16.sp
            )
            Text(
                text = stringResource(
                    MR.strings.series_fast_easy,
                    trainingDiary.seriesFastEasy.toString()
                ), fontSize = 16.sp
            )
            Text(
                text = stringResource(
                    MR.strings.series_fast_medium,
                    trainingDiary.seriesFastMedium.toString()
                ), fontSize = 16.sp
            )
            Text(
                text = stringResource(
                    MR.strings.series_fast_hard,
                    trainingDiary.seriesFastHard.toString()
                ), fontSize = 16.sp
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        TextField(
            text = newFeedback,
            onValueChange = {
                newFeedback = it
            },
            hint = stringResource(MR.strings.feedback_hint)
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(MR.colors.colorPrimaryDark),
            ),
            onClick = {
                trainingDetailsScreenModel.addFeedBack(
                    patientId = patient.uId,
                    trainingDiary = trainingDiary,
                    newFeedback
                )
            },
            content = {
                Box(contentAlignment = Alignment.Center) {
                    when (loading) {
                        true -> CircularProgressIndicator(modifier = Modifier.height(16.dp).width(16.dp))
                        false -> Text(stringResource(MR.strings.add_feedback))
                    }
                }
            }
        )

    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
    ) {
        trainingDiary.feedbacks.forEach { feedback ->
            item {
                Card(modifier = Modifier.fillMaxWidth().padding(top = 4.dp)) {
                    Text(modifier = Modifier.fillMaxWidth().padding(16.dp),
                        text = feedback, fontSize = 16.sp
                    )
                }
                Divider()
            }
        }
    }
}



