package preview

import android.content.res.Configuration
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.ui.details.patient.CreatePatientScreen

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true, showSystemUi = false
)
@Composable
fun AppAndroidPreview() {
    MaterialTheme {
        CreatePatientScreen(
            patient = Patient(
                name = "Patient ",
                uId = "UID",
                email = "patient@example.com",
                providerId = "Provider"
            ), null
        )
    }
}