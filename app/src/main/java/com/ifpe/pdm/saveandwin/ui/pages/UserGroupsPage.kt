package com.ifpe.pdm.saveandwin.ui.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.elevatedCardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ifpe.pdm.saveandwin.model.Group
import com.ifpe.pdm.saveandwin.ui.nav.Route
import com.ifpe.pdm.saveandwin.ui.theme.GrayDescriptionColor
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.LightGrayField
import com.ifpe.pdm.saveandwin.ui.theme.LightGreenBackground
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel
import kotlin.collections.remove

@Composable
fun UserGroupsPage(
    modifier: Modifier = Modifier.Companion,
    navController: NavController,
    viewModel: MainViewModel
) {
    val groups = viewModel.userGroups

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightGreenBackground)
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp),
        horizontalAlignment = CenterHorizontally,
    ) {
        item {
            Text(
                text = "Seus Grupos",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                fontSize = 30.sp
            )
            Spacer(Modifier.size(20.dp))
        }

        items(groups, key = { it.name }) { group ->
            GroupCard(
                group = group,
                onClick = {
                    viewModel.selectedGroup = group
                    Log.i("GRUPO", "Grupo selecionado ${group.name}")
                    navController.navigate(Route.GroupPage) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                            restoreState = true
                        }
                        launchSingleTop = true
                    }
                })
            Spacer(Modifier.size(15.dp))
        }
    }

}

@Composable
fun GroupCard(group: Group, onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.fillMaxWidth(),
        colors = elevatedCardColors(
            containerColor = Color.White
        ),
        onClick = onClick
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
                text = group.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "${group.members.size} integrantes",
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyMedium
            )
            group.description?.let {
                Text(
                    text = it,
                    color = GrayDescriptionColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}