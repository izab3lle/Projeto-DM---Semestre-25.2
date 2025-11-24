package com.ifpe.pdm.saveandwin.ui.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.model.User
import com.ifpe.pdm.saveandwin.ui.theme.DataStringField
import com.ifpe.pdm.saveandwin.ui.theme.DefaultButton
import com.ifpe.pdm.saveandwin.ui.theme.DefaultIconButton
import com.ifpe.pdm.saveandwin.ui.theme.GrayDescriptionColor
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel

@Composable
fun GroupMembersDialog(
    onDismiss: () -> Unit,
    members: List<User>
) {
    Dialog(onDismissRequest = { onDismiss() } ) {
        Surface(shape = RoundedCornerShape(16.dp)) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Participantes", style = MaterialTheme.typography.titleMedium)
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "",
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                LazyColumn {
                    items(members, { it.username }) { member ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                    //.clickable( onClick = { showDialog = true } )
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                            Row(Modifier.padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(member.username, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 4.dp))
                                Text("• ${member.points} pontos", style = MaterialTheme.typography.bodyMedium, color = GrayDescriptionColor)
                            }
                        }
                        HorizontalDivider(Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
    }
}