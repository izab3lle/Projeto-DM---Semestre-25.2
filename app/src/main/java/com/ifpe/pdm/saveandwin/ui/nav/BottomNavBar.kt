package com.ifpe.pdm.saveandwin.ui.nav

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ifpe.pdm.saveandwin.ui.theme.GrayDescriptionColor
import com.ifpe.pdm.saveandwin.ui.theme.GreenFocusedField
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.GreenSelected

@Composable
fun BottomNavBar(
    navController: NavHostController,
    items : List<BottomNavItem>
) {
    NavigationBar(
        contentColor = Color.Black,
        containerColor = Color.White,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination

        items.forEach { item ->
            NavigationBarItem (
                icon = { Image(painter = painterResource(item.icon), contentDescription= item.title) },
                label = { Text(text = item.title, fontSize = 12.sp) },
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
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
                colors = NavigationBarItemColors(
                    unselectedIconColor = GreenFocusedField,
                    selectedIconColor = GreenFocusedField,
                    selectedTextColor = GreenFocusedField,
                    selectedIndicatorColor = GreenSW,
                    unselectedTextColor = GreenFocusedField,
                    disabledIconColor = GrayDescriptionColor,
                    disabledTextColor = GrayDescriptionColor
                )
            )
        }
    }
}