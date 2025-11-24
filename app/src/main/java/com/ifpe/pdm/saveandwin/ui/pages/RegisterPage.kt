package com.ifpe.pdm.saveandwin.ui.pages

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.ui.theme.DataStringField
import com.ifpe.pdm.saveandwin.ui.theme.DefaultButton
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.PasswordField

@Preview(showBackground = true)
@Composable
fun RegisterPage(modifier: Modifier = Modifier.Companion) {
    var username by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val activity = LocalActivity.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp)
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
    ) {
        val defaultSpacing = 30.dp
        val fieldSpacing = 10.dp
        val titleSpacing = 60.dp

        Image(
            painter = painterResource(id = R.drawable.money_logo),
            contentDescription = "Logo Save & Win",
            modifier = Modifier.size(100.dp),
        )
        Spacer(modifier = Modifier.Companion.size(titleSpacing))

        Text(
            text = "Registre-se",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.Companion.size(titleSpacing))

        DataStringField(value = username, text = "Digite seu nome de usuário") { username = it }
        Spacer(modifier = Modifier.Companion.size(fieldSpacing))

        DataStringField(value = email, text = "Digite seu email") { email = it }
        Spacer(modifier = Modifier.Companion.size(fieldSpacing))

        PasswordField(password, "Digite sua senha") { password = it }
        Spacer(modifier = Modifier.Companion.size(fieldSpacing))

        PasswordField(confirmPassword, "Insira mais uma vez",) { confirmPassword = it }
        Spacer(modifier = Modifier.Companion.size(defaultSpacing))
        DefaultButton(
            text= "Registrar", color = GreenSW,
            onClick = {
                if (password.equals(confirmPassword)) {
                    Toast.makeText(activity, "Registro confirmado!", Toast.LENGTH_LONG).show()
                    activity.finish()
                } else {
                    Toast.makeText(
                        activity,
                        "As senhas não são iguais! Insira novamente.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            enabled = email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && username.isNotEmpty()
        )
    }
}