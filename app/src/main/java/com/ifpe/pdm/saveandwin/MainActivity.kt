package com.ifpe.pdm.saveandwin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ifpe.pdm.saveandwin.ui.dialogs.CreatePostDialog
import com.ifpe.pdm.saveandwin.ui.nav.drawer.DrawerNavItem
import com.ifpe.pdm.saveandwin.ui.nav.MainNavHost
import com.ifpe.pdm.saveandwin.ui.nav.Route
import com.ifpe.pdm.saveandwin.ui.nav.drawer.NavDrawer
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.SaveAndWinTheme
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel : MainViewModel by viewModels()   // State Hoisting - Deve ser declaraod no topo, para não ser reinicializado
            val currentRoute = navController.currentBackStackEntryAsState()
            val onCreateGroupPage = currentRoute.value?.destination?.hasRoute(Route.CreateGroup::class) == true
            var showPostDialog by remember { mutableStateOf(false) }

            // Drawer
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            SaveAndWinTheme {
                if(showPostDialog) {
                    CreatePostDialog(
                        onDismiss = { showPostDialog = false },
                        onConfirm = { showPostDialog = false },
                        groupsList = viewModel.userGroups,
                        viewModel = viewModel
                    )
                }

                NavDrawer(
                    drawerState = drawerState,
                    navController = navController,
                    scope = scope,
                    items = listOf(
                        DrawerNavItem.UserProfile,
                        DrawerNavItem.UserGroups,
                        DrawerNavItem.FindGroups,
                        DrawerNavItem.CreateGroup
                    )
                ) {
                    Scaffold(
                        topBar = {
                            @OptIn(ExperimentalMaterial3Api::class)
                            CenterAlignedTopAppBar(
                                title = {
                                    if(!onCreateGroupPage) {
                                        Image(
                                            painter = painterResource(R.drawable.money_logo),
                                            contentDescription = "Logo Save & Win",
                                            modifier = Modifier.size(40.dp)
                                        )
                                    } },
                                navigationIcon = {
                                    val icon = if(onCreateGroupPage) Icons.AutoMirrored.Filled.KeyboardArrowLeft else Icons.Default.Menu
                                    IconButton(onClick = {
                                        if(onCreateGroupPage) {
                                            navController.popBackStack()
                                        } else {
                                            scope.launch {
                                                if (drawerState.isClosed) {
                                                    drawerState.open()
                                                } else {
                                                    drawerState.close()
                                                }
                                            }
                                        }
                                    }) {
                                        Icon(icon, contentDescription = "Menu")
                                    }
                                },
                                colors = TopAppBarColors(
                                    containerColor = Color.White,
                                    scrolledContainerColor = Color.White,
                                    navigationIconContentColor = Color.Black,
                                    titleContentColor = Color.Black,
                                    actionIconContentColor = Color.Black
                                )
                            )
                        },
                        floatingActionButton = {
                            if(!onCreateGroupPage) {
                                FloatingActionButton( onClick = { showPostDialog = true }, containerColor = GreenSW, shape = CircleShape) {
                                    Icon(Icons.Default.Add, contentDescription = "Adicionar", tint = Color.White)
                                }
                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            MainNavHost(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
