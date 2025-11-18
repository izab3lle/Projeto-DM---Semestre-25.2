package com.ifpe.pdm.saveandwin.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.ifpe.pdm.saveandwin.R
import kotlinx.serialization.Serializable

val groupsIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.group)

val findGroupsIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.group)

sealed interface Route {
    @Serializable
    data object UserGroups : Route
    @Serializable
    data object FindGroups : Route
}

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: Route)
{
    data object UserGroupsButton
        : BottomNavItem("Seus Grupos", R.drawable.group, Route.UserGroups)
    data object FindGroupsButton
        : BottomNavItem("Encontrar Grupos", R.drawable.outline_group_search_24, Route.FindGroups)
}