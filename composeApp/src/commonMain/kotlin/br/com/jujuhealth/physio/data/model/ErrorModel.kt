package br.com.jujuhealth.physio.data.model

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

class ErrorModel(
    private val messageRes: StringResource
){
    @Composable
    fun getErrorMessage(): String { return stringResource(messageRes) }
}