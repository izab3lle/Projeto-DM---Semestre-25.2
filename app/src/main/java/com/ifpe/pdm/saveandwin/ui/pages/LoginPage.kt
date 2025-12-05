package com.ifpe.pdm.saveandwin.ui.pages

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.ifpe.pdm.saveandwin.MainActivity
import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.RegisterActivity
import com.ifpe.pdm.saveandwin.ui.theme.BlackButton
import com.ifpe.pdm.saveandwin.ui.theme.DataStringField
import com.ifpe.pdm.saveandwin.ui.theme.DefaultButton
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.MintGreen
import com.ifpe.pdm.saveandwin.ui.theme.PasswordField
import com.ifpe.pdm.saveandwin.viewmodel.LoginViewModel

@Composable
fun LoginPage(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val activity = LocalActivity.current as Activity

    // fade in do logo
    var visible by remember { mutableStateOf(true) }
    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0f,
        label = "alpha"
    )

    Column(modifier = Modifier.fillMaxSize().background(MintGreen)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.3f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Save\n& Win",
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                lineHeight = 35.sp
            )
            Spacer(Modifier.size(20.dp))
            Image(
                painter = painterResource(id = R.drawable.money_logo),
                contentDescription = "Logo Save & Win",
                modifier = Modifier
                    .size(140.dp)
                    .graphicsLayer { alpha = animatedAlpha }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.size(60.dp))
            Text(
                text = "Entrar",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Spacer(Modifier.size(25.dp))

            DataStringField(value = email, text =  "Insira seu email") {email = it}
            Spacer(Modifier.size(3.dp))

            PasswordField(value = password, text = "Insira sua senha") {password = it}
            Spacer(Modifier.size(10.dp))

            DefaultButton(
                text = "Login", color = BlackButton,
                onClick = {
                    Toast.makeText(activity, "Login realizado!", Toast.LENGTH_LONG).show()
                    activity.startActivity(
                        Intent(activity, MainActivity::class.java).setFlags(
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                        )
                    )
                },
                enabled = email.isNotEmpty() && password.isNotEmpty()
            )
            Spacer(Modifier.size(70.dp))

            HorizontalDivider(Modifier.padding(bottom = 10.dp), DividerDefaults.Thickness, DividerDefaults.color)
            Text("Não possui uma conta?")
            Spacer(Modifier.size(10.dp))
            DefaultButton(
                "Registrar-se", GreenSW,
                onClick = {
                    activity.startActivity(
                        Intent(activity, RegisterActivity::class.java)
                    )
                }
            )
        }

    }
}