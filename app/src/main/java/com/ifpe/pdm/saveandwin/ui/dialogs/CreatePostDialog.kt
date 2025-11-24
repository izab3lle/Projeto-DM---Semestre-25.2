package com.ifpe.pdm.saveandwin.ui.dialogs

import android.graphics.drawable.Icon
import android.view.MenuItem
import android.view.Surface
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.window.Dialog
import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.model.Post
import com.ifpe.pdm.saveandwin.ui.theme.DarkGreen
import com.ifpe.pdm.saveandwin.ui.theme.DataStringField
import com.ifpe.pdm.saveandwin.ui.theme.DefaultButton
import com.ifpe.pdm.saveandwin.ui.theme.DefaultIconButton
import com.ifpe.pdm.saveandwin.ui.theme.GrayDescriptionColor
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.GreenSelected
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel
import java.time.LocalDateTime

@Composable
fun CreatePostDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    groupsList: List<String>,
    viewModel: MainViewModel
) {
    Dialog(onDismissRequest = { onDismiss() } ) {
        Surface(shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(20.dp)) {
                var content by rememberSaveable { mutableStateOf("") }
                var group by remember { mutableStateOf("") }
                val mockupUser = viewModel.users[0]

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Compartilhe sua conquista diária!", style = MaterialTheme.typography.titleMedium)
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "",
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                ChooseGroupDropdownMenu(groupsList) { option -> group = option }
                Spacer(modifier = Modifier.height(10.dp))

                DataStringField(Modifier.height(170.dp), content, "O que você conseguiu fazer?") { content = it }
                Spacer(modifier = Modifier.height(10.dp))

                DefaultIconButton(R.drawable.outline_link,
                    "Inserir Anexo",
                    "Inserir Anexo",
                    GreenSW,
                    {})

                Spacer(modifier = Modifier.height(20.dp))
                DefaultButton(text = "Publicar", onClick = {
                    viewModel.addPost(content, mockupUser, group)
                    onConfirm()
                })
            }
        }
    }
}

@Composable
fun ChooseGroupDropdownMenu(
    groups: List<String>,
    onClick: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var label by remember { mutableStateOf("Escolher Grupo") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = { expanded = !expanded })
    ) {
        Column {
            Row(Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(Modifier.fillMaxWidth(.8f)) {
                    Icon(painterResource(R.drawable.outline_groups_24),contentDescription = null)
                    Spacer(Modifier.size(5.dp))
                    Text(label, style = MaterialTheme.typography.bodyMedium)
                }
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
            HorizontalDivider(Modifier.padding(top = 6.dp))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            groups.forEach { listGroup ->
                DropdownMenuItem(
                    text = { Text(listGroup) },
                    onClick = { label = listGroup; onClick(listGroup) }
                )
                HorizontalDivider()
            }
        }
    }
}