package com.ifpe.pdm.saveandwin.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.ifpe.pdm.saveandwin.R
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object UserGroups : Route
    @Serializable
    data object FindGroups : Route
}

sealed class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: Route)
{
    data object UserGroupsButton
        : BottomNavItem("Seus Grupos", Icons.Default.Face, Route.UserGroups)
    data object FindGroupsButton
        : BottomNavItem("Encontrar Grupos", Icons.Default.AddCircle, Route.FindGroups)
}