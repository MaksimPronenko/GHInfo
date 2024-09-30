package pronenko.ghinfo.ui.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(innerPadding: PaddingValues, navController: NavHostController) {
    val viewModel: ProfileScreenViewModel = koinViewModel<ProfileScreenViewModel>()
}