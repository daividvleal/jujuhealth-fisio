package preview

import android.content.res.Configuration
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.Patient
import br.com.jujuhealth.physio.data.model.TrainingDiary
import br.com.jujuhealth.physio.ui.details.patient.CreatePatientDetailsScreen
import br.com.jujuhealth.physio.ui.details.training.CreateTrainingDetailsScreen
import dev.icerock.moko.resources.compose.colorResource

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true, showSystemUi = false
)
@Composable
fun AppAndroidPreview() {

}