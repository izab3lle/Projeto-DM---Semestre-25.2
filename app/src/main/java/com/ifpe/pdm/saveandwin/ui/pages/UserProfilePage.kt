package com.ifpe.pdm.saveandwin.ui.pages

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.model.Group
import com.ifpe.pdm.saveandwin.model.Post
import com.ifpe.pdm.saveandwin.model.User
import com.ifpe.pdm.saveandwin.ui.dialogs.UserBadgesDialog
import com.ifpe.pdm.saveandwin.ui.dialogs.UserGroupsDialog
import com.ifpe.pdm.saveandwin.ui.nav.Route
import com.ifpe.pdm.saveandwin.ui.theme.AppBadge
import com.ifpe.pdm.saveandwin.ui.theme.GrayDescriptionColor
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.LightGreenBackground
import com.ifpe.pdm.saveandwin.ui.theme.MintGreen
import com.ifpe.pdm.saveandwin.ui.theme.SectionCard
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel
import java.time.LocalDateTime

@Composable
fun UserProfilePage(viewModel: MainViewModel, navController: NavController) {
    val user = viewModel.selectedUser
    val posts = viewModel.getUserPosts(user)
    val groups: List<Group> = viewModel.userGroups

    Column(Modifier.background(LightGreenBackground)) {
        LazyColumn {
            item { ProfileHeader(user) }
            item { UserBadges(user.username) }
            item { UserGroups(groups, viewModel, navController) }
        }
        UserPosts(posts)
    }
}

@Composable
fun ProfileHeader(user: User) {
    Box(Modifier.fillMaxWidth()) {
        Column {
            Box(Modifier
                .height(50.dp)
                .fillMaxWidth()
                .background(MintGreen))
            Box(Modifier
                .height(80.dp)
                .fillMaxWidth()
                .background(Color.White))
        }
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .border(BorderStroke(6.dp, Color.White), CircleShape)
                    .clip(CircleShape)
                    .dropShadow(
                        shape = CircleShape,
                        shadow = Shadow(
                            radius = 10.dp,
                            spread = 6.dp,
                            color = Color.Black,
                            offset = DpOffset(x = 14.dp, 14.dp)
                        )
                    ),
                painter = painterResource(id = user.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(Modifier.align(Alignment.CenterVertically)) {
                Spacer(Modifier.size(20.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        user.username,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    Spacer(Modifier.size(10.dp))
                    LevelBadge("NÍVEL 3")
                }
                Text("Membro desde ${formatDate(LocalDateTime.now())}", color = GrayDescriptionColor, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
    Spacer(Modifier.size(10.dp))
}

@Composable
fun UserBadges(username: String) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        UserBadgesDialog(username, { showDialog = false } )
    }

    SectionCard("Conquistas", true, "Ver Todas", 12, onClickMore = { showDialog = true }) {
        LazyRow(horizontalArrangement = Arrangement.Absolute.Center) {
            items(4, key = { it }) { num ->
                Icon(
                    painter = painterResource(id = R.drawable.outline_award_star_24),  // id = badge.image
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(80.dp)
                        .background(GreenSW),
                    tint = MintGreen
                )
                Spacer(Modifier.size(10.dp))
            }
        }
    }
    Spacer(Modifier.size(10.dp))
}

@Composable
fun UserGroups(groups: List<Group>, viewModel: MainViewModel, navController: NavController) {
    val displayGroups = if (groups.size > 4) groups.subList(0, 3) else groups
    var showDialog by remember { mutableStateOf(false) }

    val onClick: (Group) -> Unit = { group ->
        viewModel.selectedGroup = group
        Log.i("GRUPO", "Grupo selecionado ${group.name}")
        navController.navigate(Route.GroupPage) {
            navController.graph.route?.let {
                popUpTo(it) {
                    saveState = true
                }
                restoreState = true
            }
            launchSingleTop = true
        }
    }

    if(showDialog) {
        UserGroupsDialog(groups)
    }

    if (groups.size > 4) {
        SectionCard(
            title = "Grupos que participa",
            seeMore = true,
            moreTitle = "Ver todos",
            moreCount = groups.size,
            onClickMore = { showDialog = true }
        ) {
            GroupsCarroussel(displayGroups, onClick)
        }
    } else {
        SectionCard("Grupos que participa", false) {
            GroupsCarroussel(displayGroups, onClick)
        }
    }
    Spacer(Modifier.size(10.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsCarroussel(groups: List<Group>, onClick: (Group) -> Unit) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color.Transparent, Color.Black),
        start = Offset.Zero,
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    Column(Modifier
        .fillMaxWidth()
        .height(150.dp)) {
        HorizontalMultiBrowseCarousel(
            state = rememberCarouselState { groups.count() },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp, bottom = 16.dp),
            preferredItemWidth = 190.dp,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { i ->
            val group = groups[i]
            Box(Modifier.maskClip(MaterialTheme.shapes.large)) {
                Image(
                    modifier = Modifier
                        .height(205.dp)
                        .clickable(onClick = { onClick(group) }),
                    painter = painterResource(id = group.image),
                    contentDescription = group.name,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { alpha = 0.99f }
                        .background(gradientBrush)
                        .maskClip(MaterialTheme.shapes.large)
                )
                Text(
                    text = group.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 10.dp, bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun UserPosts(posts: List<Pair<List<Post>, Int>>) {
    var postList: MutableList<Post> = mutableListOf()
    var imageList: MutableList<Int> = mutableListOf()

    posts.forEach { post -> postList.addAll(post.first); imageList.add(post.second) }

    SectionCard("Atividade Recente", false) {
        LazyColumn(Modifier.fillMaxWidth()) {
            items(posts, {it.second}) { postList ->
                val image = postList.second
                postList.first.forEach { post ->
                    ProfilePost(post, image)
                }
            }
        }
    }
}

@Composable
fun ProfilePost(post: Post, image: Int) {
    Row(Modifier.padding(bottom = 10.dp)) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.size(10.dp))
        Column(Modifier.fillMaxWidth()) {
            Text(
                formatDate(post.created),
                style = MaterialTheme.typography.bodyMedium,
                color = GrayDescriptionColor
            )
            Text(post.content, style = MaterialTheme.typography.bodyMedium)
        }
    }
    HorizontalDivider(Modifier.padding(vertical = 5.dp))
}

@Composable
fun LevelBadge(text: String) {
    val brush = Brush.horizontalGradient(
        listOf(
            Color(0xFF2AF1FF),
            Color(0xFF4DEF78),
            Color(0xFFFBB93C)
        )
    )
    Text(
        text,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(brush)
            .padding(horizontal = 5.dp, vertical = 3.dp)
    )
}