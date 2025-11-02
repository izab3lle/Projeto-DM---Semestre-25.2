package com.ifpe.pdm.saveandwin.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun DataStringField (
    value: String,
    text: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        label = { Text(text) },
        modifier = Modifier.fillMaxWidth(fraction = .9f),
        onValueChange = onValueChange
    )
}

@Composable
fun PasswordField(
    value: String,
    text: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        label = { Text(text) },
        modifier = Modifier.fillMaxWidth(fraction = .9f),
        onValueChange = onValueChange,
        visualTransformation = PasswordVisualTransformation()
    )
}