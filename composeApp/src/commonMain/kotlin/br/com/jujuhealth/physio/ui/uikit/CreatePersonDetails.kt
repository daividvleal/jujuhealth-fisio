package br.com.jujuhealth.physio.ui.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.jujuhealth.physio.MR
import dev.icerock.moko.resources.compose.colorResource

@Composable
fun CreatePersonDetails(
    personName: String? = null,
    personEmail: String? = null
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 24.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(colorResource(MR.colors.colorPrimary)),
        ) {
            Text(
                text = generateInitials(personName),
                color = Color.White,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            fontSize = 18.sp,
            text = personName.orEmpty()
        )
        Text(text = personEmail.orEmpty(), fontSize = 18.sp)
    }
}

fun generateInitials(name: String?): String {
    var result = ""
    val words = name?.split(" ")
    words?.let {
        result = if (words.size >= 2) {
            "${words[0][0]}${words[1][0]}"
        } else {
            "${words[0][0]}"
        }
    }
    return result
}