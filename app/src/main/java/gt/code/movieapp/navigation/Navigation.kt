package gt.code.movieapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import gt.code.movieapp.models.MovieViewModel
import gt.code.movieapp.screens.*

//const val movieId = "movieId"
@Composable
fun MyNavigation() {
    val movieViewModel: MovieViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, viewModel =movieViewModel)
        }

        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(MOVIE_ID){
                type = NavType.StringType
            })
        )  { backStackEntry ->
            DetailScreen(navController = navController,
                movieId = backStackEntry.arguments?.getString(MOVIE_ID), viewModel=movieViewModel)
        }
        composable(
            route = Screen.Favorites.route) {
            FavoriteScreen(navController = navController, viewModel=movieViewModel) }
        composable(
            Screen.AddMovie.route){
            AddMovieScreen(navController = navController,viewModel=movieViewModel) }

            }

    }
