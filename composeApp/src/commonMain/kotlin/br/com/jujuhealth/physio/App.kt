package br.com.jujuhealth.physio

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import br.com.jujuhealth.physio.login.LoginScreenRoute
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun App() {
    // Manually configure Firebase Options. The following fields are REQUIRED:
//   - Project ID
//   - App ID
//   - API Key
    // Manually configure Firebase Options. The following fields are REQUIRED:
//   - Project ID
//   - App ID
//   - API Key
//    val options: FirebaseOptions = Builder()
//        .setProjectId("my-firebase-project")
//        .setApplicationId("1:27992087142:android:ce3b6448250083d1")
//        .setApiKey("AIzaSyADUe90ULnQDuGShD9W23RDP0xmeDc6Mvw") // setDatabaseURL(...)
//        // setStorageBucket(...)
//        .build()
    MaterialTheme {
        Navigator(LoginScreenRoute)
    }
}