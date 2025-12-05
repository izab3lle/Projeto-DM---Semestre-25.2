package com.ifpe.pdm.saveandwin.ui.theme


import android.app.Dialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.internal.OpDescriptor

@Composable
fun DataStringField (
    modifier: Modifier = Modifier,
    value: String,
    text: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        label = { Text(text) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = DarkGreen,
        )
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
        modifier = Modifier.fillMaxWidth(fraction = 1f),
        shape = RoundedCornerShape(14.dp),
        onValueChange = onValueChange,
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = DarkGreen,
        )
    )
}

@Composable
fun DefaultButton(
    text: String,
    color: Color = BlackButton,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        ),
        onClick = onClick,
        enabled = enabled
    ) { Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
}

@Composable
fun DefaultIconButton(
    icon: Int,
    iconDescription: String,
    text: String,
    color: Color = BlackButton,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = Modifier.height(46.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        ),
        onClick = onClick,
        enabled = enabled
    ) {
        Icon(painter = painterResource(icon), contentDescription = iconDescription, modifier = Modifier.padding(end = 16.dp))
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun DialogButton(
    text: String,
    color: Color = BlackButton,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = Modifier
            .height(40.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        ),
        onClick = onClick,
        enabled = enabled
    ) { Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold) }
}

@Composable
fun SectionCard(
    title: String,
    seeMore: Boolean,
    moreTitle: String = "",
    moreCount: Int = 0,
    onClickMore: () -> Unit = { },
    content: @Composable () -> Unit
) {
    Column(Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            if(seeMore) {
                Row(Modifier.clickable(
                    enabled = true,
                    role = Role.Button,
                    onClick = { onClickMore() },
                    onClickLabel = moreTitle
                )) {
                    Text("$moreTitle ($moreCount)", fontWeight = FontWeight.Bold, color = GreenSW)
                    Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = GreenSW)
                }
            }
        }
        HorizontalDivider(Modifier.padding(vertical = 8.dp))
        content()
    }
}