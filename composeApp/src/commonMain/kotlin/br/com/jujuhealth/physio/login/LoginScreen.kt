package br.com.jujuhealth.physio.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.jujuhealth.physio.MR
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.resources.compose.stringResource

data object LoginScreen: Screen {
    @Composable
    override fun Content(){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.padding(start = 16.dp, top = 32.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(MR.strings.app_name)
                )
            }

            Row(
                modifier = Modifier.fillMaxSize().padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {

                        },
                        placeholder = {
                            Text("E-mail")
                        }
                    )

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {

                        },
                        placeholder = {
                            Text("Password:")
                        }
                    )
                }
            }
        }
    }
}