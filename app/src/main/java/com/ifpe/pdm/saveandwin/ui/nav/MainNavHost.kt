package com.ifpe.pdm.saveandwin.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ifpe.pdm.saveandwin.ui.pages.FindGroupsPage
import com.ifpe.pdm.saveandwin.ui.pages.UserGroupsPage
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel

@Composable
fun MainNavHost(navController: NavHostController, viewModel : MainViewModel) {
    NavHost(navController, startDestination = Route.UserGroups) {
        composable<Route.UserGroups> { UserGroupsPage(viewModel = viewModel) }
        composable<Route.FindGroups> { FindGroupsPage(viewModel = viewModel) }
    }
}