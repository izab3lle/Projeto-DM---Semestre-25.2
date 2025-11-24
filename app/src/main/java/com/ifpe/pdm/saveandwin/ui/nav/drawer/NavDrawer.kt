package com.ifpe.pdm.saveandwin.ui.nav.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ifpe.pdm.saveandwin.ui.nav.Route
import com.ifpe.pdm.saveandwin.ui.theme.DarkGreen
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.MintGreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavDrawer(
    drawerState: DrawerState,
    navController: NavController,
    items : List<DrawerNavItem>,
    scope: CoroutineScope,
    content: @Composable () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    val createGroupColors: NavigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
        unselectedContainerColor = GreenSW,
        unselectedTextColor = Color.White,
        unselectedIconColor = Color.White,
    )

    val defaultColors = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = MintGreen
    )

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Bem Vindo, Usuário!",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider()
                    Spacer(Modifier.size(15.dp))

                    //Text("Section 1", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)

                    items.forEach { item ->
                        val iconColor = if (item.route == Route.CreateGroup) Color.White else DarkGreen
                        if(item.route == Route.FindGroups) {
                            Spacer(Modifier.size(30.dp))
                        }

                        NavigationDrawerItem (
                            icon = { Image(painter = painterResource(item.icon), contentDescription = item.title, colorFilter = ColorFilter.tint(iconColor))},
                            label = { Text(item.title, fontWeight = FontWeight.Normal) },
                            selected = currentRoute == item.route,
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }

                                navController.navigate(item.route) {
                                    navController.graph.startDestinationRoute?.let {
                                        popUpTo(it) {
                                            saveState = true
                                        }
                                        restoreState = true
                                    }
                                    launchSingleTop = true
                                }
                            },
                            colors = if (item.route == Route.CreateGroup) createGroupColors else defaultColors
                        )
                    }

                    Spacer(Modifier.size(30.dp))
                    NavigationDrawerItem(
                        label = { Text("Notificações") },
                        selected = false,
                        icon = { Icon(Icons.Default.Notifications, contentDescription = null, tint = DarkGreen)},
                        onClick = { /* Handle click */ },
                        badge = { Text("20") },
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        content()
    }
}