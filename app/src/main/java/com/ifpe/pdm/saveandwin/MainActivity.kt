package com.ifpe.pdm.saveandwin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ifpe.pdm.saveandwin.ui.nav.BottomNavBar
import com.ifpe.pdm.saveandwin.ui.nav.BottomNavItem
import com.ifpe.pdm.saveandwin.ui.nav.MainNavHost
import com.ifpe.pdm.saveandwin.ui.theme.GreenSW
import com.ifpe.pdm.saveandwin.ui.theme.SaveAndWinTheme
import com.ifpe.pdm.saveandwin.viewmodel.GroupPageViewModel
import com.ifpe.pdm.saveandwin.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel : MainViewModel by viewModels()   // State Hoisting - Deve ser declaraod no topo, para não ser reinicializado
            val groupPageViewModel : GroupPageViewModel by viewModels()

            SaveAndWinTheme {

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
                                IconButton(onClick = { this.finish() }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Localized description"
                                    )
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
                    bottomBar = {
                        val items = listOf(
                            BottomNavItem.UserGroupsButton,
                            BottomNavItem.FindGroupsButton,
                        )
                        BottomNavBar(navController = navController, items)
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