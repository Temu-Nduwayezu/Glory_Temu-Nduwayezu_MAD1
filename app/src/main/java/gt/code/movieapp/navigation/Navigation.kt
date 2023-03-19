package gt.code.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import gt.code.movieapp.screens.*

//const val movieId = "movieId"
@Composable
fun MyNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController= navController)
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(movieId){
                type = NavType.StringType
            })
        )  { backStackEntry ->
            DetailScreen(navController = navController,
                movieId = backStackEntry.arguments?.getString(movieId))
        }
        composable(
            route = Screen.Favorites.route) {
            FavoriteScreen(navController = navController) }
    }
}