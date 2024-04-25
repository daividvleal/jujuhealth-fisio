package br.com.jujuhealth.physio.ui.add_patient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.ui.uikit.CreateTopBar
import br.com.jujuhealth.physio.ui.uikit.TextField
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.stringResource

data object AddPatientScreenRoute : Screen {
    @Composable
    override fun Content() {
        val addPatientScreenModel: AddPatientScreenModel = getScreenModel()
        CreateAddPatientScreenRoute(addPatientScreenModel)
    }
}

@Composable
fun CreateAddPatientScreenRoute(addPatientScreenModel: AddPatientScreenModel) {

    val navigator = LocalNavigator.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CreateTopBar(
                title = stringResource(MR.strings.home_toolbar_name),
                onNavigationIconClick = { navigator?.pop() }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().background(colorResource(MR.colors.colorPrimary)),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                text = stringResource(MR.strings.app_name)
            )
            CreateForm(addPatientScreenModel)
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 16.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                text = stringResource(MR.strings.app_name)
            )
        }

    }
}

@Composable
fun CreateForm(addPatientScreenModel: AddPatientScreenModel) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var pwd by remember { mutableStateOf("") }
        var confirmPwd by remember { mutableStateOf("") }

        TextField(
            text = name,
            onValueChange = { name = it },
            hint = stringResource(MR.strings.name)
        )

        TextField(
            text = email,
            onValueChange = { email = it },
            hint = stringResource(MR.strings.email),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )

        TextField(
            text = pwd,
            onValueChange = { pwd = it },
            hint = stringResource(MR.strings.password),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
        )

        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(MR.colors.colorPrimaryDark),
                contentColor = Color.White
            ),
            onClick = {
                addPatientScreenModel.addPatient(Patient(
                    name = name,
                    email = email
                ), pwd, confirmPwd)
            }) {
            Text(stringResource(MR.strings.password))
        }

        AlertDialog(
            onDismissRequest = {},
            title = { Text("Confirmation") },
            text = { Text("Are you sure you want to proceed?") },
            confirmButton = {
                Button(onClick = {

                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = {

                }) {
                    Text("Cancel")
                }
            }
        )
    }
}