package preview

import android.content.res.Configuration
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.User
import br.com.jujuhealth.physio.ui.home.CreateHomeScreen
import br.com.jujuhealth.physio.ui.login.LoginScreenRoute
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true, showSystemUi = false
)
@Composable
fun AppAndroidPreview() {
    MaterialTheme {
        CreateHomeScreen(
            user = User(
                name = "Daivid Vasconcelos Leal",
                uId = "YUYT4fggDLI0977FSD7SDGHNID6GAFDH",
                email = "daivid@gmail.com",
                providerId = "Firebase",
                patients = mutableListOf()
            ).apply {
                // Simulating data for 20 patients and adding them to the list
                for (i in 1..20) {
                    val patient = Patient(
                        name = "Patient $i",
                        uId = "UID$i",
                        email = "patient$i@example.com",
                        providerId = "Provider$i"
                    )
                    this.mutablePatientList.add(patient)
                }
            }
        )
    }
}