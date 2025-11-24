package com.ifpe.pdm.saveandwin.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.model.Badge
import com.ifpe.pdm.saveandwin.model.Group
import com.ifpe.pdm.saveandwin.model.Post
import com.ifpe.pdm.saveandwin.model.User
import com.ifpe.pdm.saveandwin.ui.dialogs.UserBadgesDialog
import com.ifpe.pdm.saveandwin.ui.nav.Route
import com.ifpe.pdm.saveandwin.ui.theme.SectionCard
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel

@Composable
fun UserProfilePage(viewModel: MainViewModel, navController: NavController) {
    val user = viewModel.getMockupUser()
    val posts: List<Post> = viewModel.getUserPosts(user)
    val badges: List<Badge> = user.badges
    val groups: List<Group> = viewModel.userGroups

    LazyColumn {
        item { ProfileHeader(user) }
        item { UserBadges(badges) }
        item { UserGroups(groups, viewModel, navController) }
        item { UserPosts(posts) }
    }
}

@Composable
fun ProfileHeader(user: User) {

}

@Composable
fun UserBadges(badges: List<Badge>) {
    var showDialog by remember { mutableStateOf(false) }

    if(showDialog) {
        UserBadgesDialog(badges)
    }
    SectionCard("Conquistas", true, "Ver Todas", badges.size, onClickMore = { showDialog = true }) {
        LazyRow {
            items(badges, { it.badge }) { badge ->
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(100.dp),
                    painter = painterResource(id = R.drawable.ic_launcher_background),  // id = badge.image
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun UserGroups(groups: List<Group>, viewModel: MainViewModel, navController: NavController) {
    val displayGroups = if(groups.size > 4)  groups.subList(0, 3) else groups
    var showDialog by remember { mutableStateOf(false) }

    val onClick: (Group) -> Unit = { group ->
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
    }

    if(groups.size > 4) {
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsCarroussel(groups: List<Group>, onClick: (Group) -> Unit) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color.Transparent, Color.Black),
        start = Offset.Zero,
        end = Offset(0f, Float.POSITIVE_INFINITY)
    )

    Column(Modifier.fillMaxWidth().height(150.dp)) {
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
                    modifier = Modifier.align(Alignment.BottomStart).padding(start = 10.dp, bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun UserPosts(posts: List<Post>) {
    SectionCard("Atividade Recente", false) { }
}
