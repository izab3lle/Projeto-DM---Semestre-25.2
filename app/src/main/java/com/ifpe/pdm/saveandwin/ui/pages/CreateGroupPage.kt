package com.ifpe.pdm.saveandwin.ui.pages

import android.R.attr.text
import android.accessibilityservice.GestureDescription
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ifpe.pdm.saveandwin.model.GroupVisibility
import com.ifpe.pdm.saveandwin.ui.theme.DataStringField
import com.ifpe.pdm.saveandwin.ui.theme.DefaultButton
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.GreenSelected
import com.ifpe.pdm.saveandwin.ui.theme.LightGrayField
import com.ifpe.pdm.saveandwin.ui.theme.MintGreen
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel

@Composable
fun CreateGroupPage(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var name by rememberSaveable { mutableStateOf("") }
        var description by rememberSaveable { mutableStateOf("") }
        var visibility by rememberSaveable { mutableStateOf(GroupVisibility.PUBLIC) }
        var scoreOptions: MutableList<String> = mutableListOf()

        ImageInput()
        Spacer(Modifier.size(5.dp))

        DataStringField(value = name, text = "Nome do Grupo") { name = it }
        Spacer(Modifier.size(5.dp))

        DataStringField(Modifier.height(170.dp), description, "Nome do Grupo") { name = it }
        Spacer(Modifier.size(20.dp))

        VisibilityOptionsList(visibility) { visibility = it }
        Spacer(Modifier.size(8.dp))

        DefaultButton("Criar Grupo", onClick = {  })
    }
}

@Composable
fun ImageInput() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MintGreen)
            .height(120.dp)
            .clip(RoundedCornerShape(30.dp)),
    ) {

    }
}

@Composable
fun VisibilityOptionsList (
    visibility: GroupVisibility,
    onOptionSelected: (GroupVisibility) -> Unit
) {
    fun options() = listOf(
        GroupVisibility.PUBLIC to "Seu grupo ficará disponível na aba de comunidades e aparecerá como resultado de pesquisas",
        GroupVisibility.PRIVATE to "Apenas quem possuir o link poderá acessá-lo"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .selectableGroup()
    ) {
        Text("Visibilidade do Grupo", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.size(10.dp))

        options().forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .selectable(
                        selected = (visibility == option.first),
                        onClick = { onOptionSelected(option.first) },
                        role = Role.RadioButton
                    )
                    .background(LightGrayField)
                    .padding(vertical = 5.dp)
                    .padding(end = 5.dp),
                verticalAlignment = Alignment.Top
            ) {
                RadioButton(
                    selected = (option.first == visibility),
                    onClick = { onOptionSelected(option.first) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = GreenSW,
                    )
                )
                Column {
                    Text(text = option.first.type, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = option.second, style = MaterialTheme.typography.bodyMedium)
                }
            }
            Spacer(Modifier.size(10.dp))
        }
    }
}
