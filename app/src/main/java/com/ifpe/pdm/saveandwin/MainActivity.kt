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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ifpe.pdm.saveandwin.ui.nav.bottom.BottomNavBar
import com.ifpe.pdm.saveandwin.ui.nav.bottom.BottomNavItem
import com.ifpe.pdm.saveandwin.ui.nav.drawer.DrawerNavItem
import com.ifpe.pdm.saveandwin.ui.nav.MainNavHost
import com.ifpe.pdm.saveandwin.ui.nav.drawer.NavDrawer
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.SaveAndWinTheme
import com.ifpe.pdm.saveandwin.viewmodel.GroupPageViewModel
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel : MainViewModel by viewModels()   // State Hoisting - Deve ser declaraod no topo, para não ser reinicializado
            val groupPageViewModel : GroupPageViewModel by viewModels()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            SaveAndWinTheme {
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
                                    Image(
                                        painter = painterResource(R.drawable.money_logo),
                                        contentDescription = "Logo Save & Win",
                                        modifier = Modifier.size(40.dp)
                                    )
                                        },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            if (drawerState.isClosed) {
                                                drawerState.open()
                                            } else {
                                                drawerState.close()
                                            }
                                        }
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
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
                            FloatingActionButton(
                                onClick = { },
                                containerColor = GreenSW,
                                shape = CircleShape
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Adicionar")
                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            MainNavHost(
                                navController = navController,
                                viewModel = viewModel,
                                groupPageViewModel = groupPageViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
