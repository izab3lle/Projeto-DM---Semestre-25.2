package com.ifpe.pdm.saveandwin.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.model.Group
import com.ifpe.pdm.saveandwin.model.Post
import com.ifpe.pdm.saveandwin.model.User
import com.ifpe.pdm.saveandwin.ui.dialogs.GroupMembersDialog
import com.ifpe.pdm.saveandwin.ui.nav.Route
import com.ifpe.pdm.saveandwin.ui.theme.GrayDescriptionColor
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.LightGreenBackground
import com.ifpe.pdm.saveandwin.ui.theme.MintGreen
import com.ifpe.pdm.saveandwin.ui.theme.SectionCard
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun GroupPage(group: Group?) {
    Column(Modifier.background(LightGreenBackground)) {
        GroupHeader(group)
        Spacer(Modifier.size(15.dp))
        MembersBar(group?.members ?: listOf())
        Spacer(Modifier.size(15.dp))
        PostsList(group?.posts ?: listOf())
    }
}

@Composable
fun GroupHeader(group: Group?) {
    Column(Modifier.background(Color.White)) {
        val gradientBrush = Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Black),
            start = Offset.Zero,
            end = Offset(0f, Float.POSITIVE_INFINITY)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
        ) {
            Image(
                painter = painterResource(group?.image ?: R.drawable.ic_launcher_background),
                contentDescription = "Imagem do grupo",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = 0.99f }
                    .background(gradientBrush)
            )
            Text(group?.name.toString(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomStart).padding(start = 10.dp, bottom = 10.dp)
            )
        }
        Column(Modifier.padding(vertical = 10.dp, horizontal = 10.dp)) {
            Row {
                Text("Criado em ${group?.getCreatedDateString()} por ", color = GrayDescriptionColor, style = MaterialTheme.typography.bodyMedium)
                Text("${group?.creator?.username}", fontWeight = FontWeight.Bold, color = GreenSW, style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(Modifier.size(5.dp))
            Text(group?.description.toString())
        }
    }
}

@Composable
fun MembersBar(members: List<User>) {
    var showDialog by remember { mutableStateOf(false) }
    if(showDialog) {
        GroupMembersDialog({ showDialog = false }, members)
    }

    SectionCard("Participantes", true, "Ver todos", members.size, onClickMore = { showDialog = true} ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            for(i in 0 .. 5) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                        .clickable( onClick = { showDialog = true } ),
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun PostsList(posts: List<Post>) {
    SectionCard("Atividades", false) {
        if(posts.isEmpty()) {
            Text("O grupo ainda não possui pastagens.")
        } else {
            LazyColumn {
                items(posts, { it.created }) { post -> GroupPost(post); HorizontalDivider() }
            }
        }
    }
}

@Composable
fun GroupPost(post: Post) {
    Row(Modifier.padding(bottom = 10.dp)) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
                .clickable( onClick = { Log.i("USER", "Clicou em ${post.user.username}!") } ),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.size(10.dp))
        Column {
            Row {
                Text(post.user.username, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.size(4.dp))
                Text(formatDate(post.created), style = MaterialTheme.typography.bodyMedium, color = GrayDescriptionColor)
            }
            Text(post.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.size(3.dp))
            Row(Modifier.fillMaxWidth()) {
               PostLink("Responder") { }
                Spacer(Modifier.size(10.dp))
               PostLink("Reagir") { }
            }
        }
    }
}
@Composable
fun PostLink(title: String, onClick: () -> Unit) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        color = GreenSW,
        modifier = Modifier.clickable( onClick = { onClick() })
    )
}

fun formatDate(date: LocalDateTime): String {
    val dateFormatter = DateTimeFormatter.ofPattern("dd/mm/YYYY, HH:mm")
    return date.format(dateFormatter)
}