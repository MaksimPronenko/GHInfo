package pronenko.ghinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pronenko.ghinfo.ui.common.MainText
import pronenko.ghinfo.ui.common.SCREEN_SEARCH
import pronenko.ghinfo.ui.search.SearchScreen
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import pronenko.ghinfo.ui.common.KEY_LOGIN
import pronenko.ghinfo.ui.common.SCREEN_DETAIL
import pronenko.ghinfo.ui.common.SCREEN_PROFILE
import pronenko.ghinfo.ui.details.DetailsScreen
import pronenko.ghinfo.ui.profile.ProfileScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            NavHost(navController, startDestination = SCREEN_SEARCH) {
                composable(SCREEN_SEARCH) { SearchScreen(innerPadding, navController) }
                composable("$SCREEN_DETAIL/{$KEY_LOGIN}")
                { backStackEntry ->
                    val city = backStackEntry.arguments?.getString(KEY_LOGIN) ?: ""
                    DetailsScreen(innerPadding, city)
                }
                composable(SCREEN_PROFILE) { ProfileScreen(innerPadding, navController) }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Black)
            )
            BottomNavigation(backgroundColor = Color.White) {
                BottomNavigationItem(
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                color = if (currentRoute == SCREEN_SEARCH) {
                                    Color.Blue
                                } else {
                                    Color.Black
                                }
                            )
                        )
                    },
                    label = {
                        MainText(
                            text = getString(R.string.search),
                            color = if (currentRoute == SCREEN_SEARCH) {
                                Color.Blue
                            } else {
                                Color.Black
                            }
                        )
                    },
                    selected = currentRoute == SCREEN_SEARCH,
                    onClick = {
                        if (currentRoute != SCREEN_SEARCH) {
                            navController.navigate(SCREEN_SEARCH)
                        }
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                color = if (currentRoute == SCREEN_PROFILE) {
                                    Color.Blue
                                } else {
                                    Color.Black
                                }
                            )
                        )
                    },
                    label = {
                        MainText(
                            text = getString(R.string.profile),
                            color = if (currentRoute == SCREEN_PROFILE) {
                                Color.Blue
                            } else {
                                Color.Black
                            }
                        )
                    },
                    selected = currentRoute == SCREEN_PROFILE,
                    onClick = {
                        if (currentRoute != SCREEN_PROFILE) {
                            navController.navigate(SCREEN_PROFILE)
                        }
                    }
                )
            }
        }
    }
}
