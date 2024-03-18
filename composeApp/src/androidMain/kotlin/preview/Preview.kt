package preview

import android.content.res.Configuration
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.jujuhealth.physio.login.LoginScreen

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true, showSystemUi = false
)
@Composable
fun AppAndroidPreview() {
    MaterialTheme {
        LoginScreen.Content()
    }
}