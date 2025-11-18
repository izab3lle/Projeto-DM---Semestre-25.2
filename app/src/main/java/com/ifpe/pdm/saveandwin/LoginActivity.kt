package com.ifpe.pdm.saveandwin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ifpe.pdm.saveandwin.ui.theme.SaveAndWinTheme
import com.ifpe.pdm.saveandwin.ui.pages.LoginPage
import com.ifpe.pdm.saveandwin.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SaveAndWinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginPage(Modifier.padding(innerPadding))
                }
            }
        }
    }
}