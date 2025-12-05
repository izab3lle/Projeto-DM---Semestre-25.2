package com.ifpe.pdm.saveandwin.ui.dialogs

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.model.Group
import com.ifpe.pdm.saveandwin.ui.theme.DataStringField
import com.ifpe.pdm.saveandwin.ui.theme.DefaultButton
import com.ifpe.pdm.saveandwin.ui.theme.DefaultIconButton
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel

@Composable
fun CreatePostDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    groupsList: List<Group>,
    viewModel: MainViewModel
) {
    Dialog(onDismissRequest = { onDismiss() } ) {
        Surface(shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(20.dp)) {
                var content by rememberSaveable { mutableStateOf("") }
                var group by remember { mutableStateOf("") }
                val activity = LocalActivity.current as Activity
                val mockupUser = viewModel.loggedUser

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
                    var message: String? = ""
                    if(group.isNotEmpty()) {
                        try {
                            viewModel.searchGroup(group)?.let { viewModel.addPost(content, mockupUser, it) }
                            message = "Post adicionado com sucesso"
                        } catch (e: Exception) {
                            e.printStackTrace()
                            message = e.message
                        } finally {
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                        }
                        onConfirm()
                    } else {
                        Toast.makeText(activity, "Escolha um grupo para postar!", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}

@Composable
fun ChooseGroupDropdownMenu(
    groups: List<Group>,
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
                    text = { Text(listGroup.name) },
                    onClick = { label = listGroup.name; onClick(listGroup.name) }
                )
                HorizontalDivider()
            }
        }
    }
}