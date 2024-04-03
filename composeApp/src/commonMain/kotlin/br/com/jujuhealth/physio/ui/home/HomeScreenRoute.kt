package br.com.jujuhealth.physio.ui.home

import CreateGenericError
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.jujuhealth.physio.MR
import br.com.jujuhealth.physio.data.model.ErrorModel
import br.com.jujuhealth.physio.data.model.User
import cafe.adriel.voyager.core.screen.Screen

data class HomeScreenRoute(val user: User?) : Screen {
    @Composable
    override fun Content() {
        user?.let {
            CreateHomeScreen(user)
        } ?: run {
            CreateGenericError(ErrorModel(MR.strings.general_error_message))
        }
    }
}

@Composable
fun CreateHomeScreen(
    user: User
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = user.name.orEmpty())
            Text(text = user.email.orEmpty())
        }
    }
}