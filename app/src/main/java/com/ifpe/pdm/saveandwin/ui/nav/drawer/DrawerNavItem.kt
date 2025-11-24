package com.ifpe.pdm.saveandwin.ui.nav.drawer

import com.ifpe.pdm.saveandwin.R
import com.ifpe.pdm.saveandwin.ui.nav.Route

sealed class DrawerNavItem(
    val title: String,
    val icon: Int,
    val route: Route
)
{
    data object UserProfile
        : DrawerNavItem("Seu Perfil", R.drawable.outline_face, Route.UserProfile)
    data object UserGroups
        : DrawerNavItem("Seus Grupos", R.drawable.group, Route.UserGroups)
    data object FindGroups
        : DrawerNavItem("Encontrar Grupos", R.drawable.outline_group_search_24, Route.FindGroups)
    data object CreateGroup
        : DrawerNavItem("Criar Grupo", R.drawable.outline_add_circle, Route.CreateGroup)
}