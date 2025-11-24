package com.ifpe.pdm.saveandwin.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.elevatedCardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ifpe.pdm.saveandwin.model.Group
import com.ifpe.pdm.saveandwin.ui.dialogs.EnterGroupDialog
import com.ifpe.pdm.saveandwin.ui.theme.DefaultButton
import com.ifpe.pdm.saveandwin.ui.theme.GrayDescriptionColor
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.LightGreenBackground
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel

@Composable
fun FindGroupsPage(modifier: Modifier = Modifier.Companion, viewModel: MainViewModel) {
    val groups = viewModel.allGroups
    val user = viewModel.getMockupUser()

    Column(
        modifier = modifier.fillMaxSize()
            .background(LightGreenBackground)
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp),
        horizontalAlignment = CenterHorizontally,
    ) {
        Spacer(Modifier.size(20.dp))
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            items(groups, key = { it.name }) { group ->
                EnterGroupCard(
                    group = group,
                    onClick = {
                        viewModel.enterGroup(user, group)
                        Log.i("GRUPO", "${user.username} entrou em ${group.name}")
                    })
                Spacer(Modifier.size(15.dp))
            }
        }
    }
}

@Composable
fun EnterGroupCard(group: Group, onClick: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        EnterGroupDialog(group.name, group.image,
            onDismiss = { showDialog = false }, onConfirm = { onClick() }
        )
    }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.fillMaxWidth(),
        colors = elevatedCardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
        ) {
            Image(
                painter = painterResource(group.image),
                contentDescription = "Imagem do grupo",
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Text(
                text = group.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${group.members.size} integrantes", fontWeight = FontWeight.Medium, style = MaterialTheme.typography.bodyMedium
            )
            group.description?.let {
                Text(
                    text = it,
                    color = GrayDescriptionColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(Modifier.size(8.dp))
            DefaultButton("Entrar no grupo", GreenSW,{ showDialog = true})
        }
    }
}
