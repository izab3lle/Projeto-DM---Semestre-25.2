package com.ifpe.pdm.saveandwin.ui.pages

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ifpe.pdm.saveandwin.MainActivity
import com.ifpe.pdm.saveandwin.ui.theme.DataStringField
import com.ifpe.pdm.saveandwin.ui.theme.PasswordField
import com.ifpe.pdm.saveandwin.viewmodel.LoginViewModel

@Composable
fun LoginPage(modifier: Modifier = Modifier, viewModel: LoginViewModel) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var loggedUser by rememberSaveable { mutableStateOf("") }
    val activity = LocalActivity.current as Activity

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login")
        DataStringField(email, "Insira seu email") {email = it}
        PasswordField(password, "Insira sua senha") {password = it}

        Button(
            modifier = Modifier.Companion.fillMaxWidth(.9f),
            shape = RoundedCornerShape(9.dp),
            onClick = {
                if(viewModel.login(email, password)) {
                    Toast.makeText(activity, "Login realizado!", Toast.LENGTH_LONG).show()
                    activity.startActivity(
                        Intent(activity, MainActivity::class.java).setFlags(
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                        )
                    )
                } else {
                    Toast.makeText(activity, "Email ou senha estão incorretos", Toast.LENGTH_LONG).show()
                }
            },
            enabled = email.isNotEmpty() && password.isNotEmpty()
        ) {
            Text("Login")
        }
    }
}