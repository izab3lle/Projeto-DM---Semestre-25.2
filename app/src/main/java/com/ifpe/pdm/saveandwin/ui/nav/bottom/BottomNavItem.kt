package com.ifpe.pdm.saveandwin.ui.nav.bottom

import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.ui.nav.Route

sealed class BottomNavItem(
    val title: String,
    val icon: Int,
    val route: Route
)
{
    data object UserGroupsButton
        : BottomNavItem("Seus Grupos", R.drawable.group, Route.UserGroups)
    data object FindGroupsButton
        : BottomNavItem("Encontrar Grupos", R.drawable.outline_group_search_24, Route.FindGroups)
}