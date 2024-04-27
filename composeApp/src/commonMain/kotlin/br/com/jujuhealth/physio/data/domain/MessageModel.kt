package br.com.jujuhealth.physio.data.domain

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class MessageModel(
    private val messageRes: @Contextual StringResource
){
    @Composable
    fun getMessage(): String { return stringResource(messageRes) }
}