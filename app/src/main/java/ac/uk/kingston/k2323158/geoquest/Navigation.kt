package ac.uk.kingston.k2323158.geoquest

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ac.uk.kingston.k2323158.geoquest.ui.screens.LandingScreen
import ac.uk.kingston.k2323158.geoquest.ui.screens.UsernameScreen
import ac.uk.kingston.k2323158.geoquest.ui.screens.GlobalModeScreen
import ac.uk.kingston.k2323158.geoquest.viewmodel.MapViewModel
import ac.uk.kingston.k2323158.geoquest.viewmodel.LeaderboardViewModel
import ac.uk.kingston.k2323158.geoquest.viewmodel.ProfileViewModel

object Routes {
    const val LANDING = "landing"
    const val USERNAME = "username"
    const val GLOBAL_MODE = "global_mode"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val mapViewModel: MapViewModel = viewModel()
    val leaderboardViewModel: LeaderboardViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.LANDING
    ) {
        composable(Routes.LANDING) {
            LandingScreen(
                onModeSelected = { navController.navigate(Routes.USERNAME) }
            )
        }
        composable(Routes.USERNAME) {
            UsernameScreen(
                onUsernameEntered = { username ->
                    profileViewModel.setUsername(username)
                    navController.navigate(Routes.GLOBAL_MODE)
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.GLOBAL_MODE) {
            GlobalModeScreen(
                mapViewModel = mapViewModel,
                leaderboardViewModel = leaderboardViewModel,
                profileViewModel = profileViewModel
            )
        }
    }
}