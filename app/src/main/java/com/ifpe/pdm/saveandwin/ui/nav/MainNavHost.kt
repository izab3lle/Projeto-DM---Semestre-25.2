package com.ifpe.pdm.saveandwin.ui.nav

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ifpe.pdm.saveandwin.ui.pages.CreateGroupPage
import com.ifpe.pdm.saveandwin.ui.pages.FindGroupsPage
import com.ifpe.pdm.saveandwin.ui.pages.GroupPage
import com.ifpe.pdm.saveandwin.ui.pages.UserGroupsPage
import com.ifpe.pdm.saveandwin.ui.pages.UserProfilePage
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    viewModel : MainViewModel
) {
    NavHost(navController, startDestination = Route.UserGroups) {
        composable<Route.UserGroups> { UserGroupsPage(viewModel = viewModel, navController = navController) }
        composable<Route.FindGroups> { FindGroupsPage(viewModel = viewModel) }
        composable<Route.UserProfile> { UserProfilePage(viewModel = viewModel, navController) }
        composable<Route.GroupPage> { GroupPage(group = viewModel.selectedGroup) }
        composable<Route.CreateGroup> { CreateGroupPage(viewModel = viewModel) }
        composable<Route.Notifications> { Text("Notificações") }
    }
}