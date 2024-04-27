package br.com.jujuhealth.physio.ui.uikit

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CreateTopBar(title: String, icon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack, onNavigationIconClick: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = title, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(icon, contentDescription = "Back")
            }
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        contentColor = Color.White
    )
}