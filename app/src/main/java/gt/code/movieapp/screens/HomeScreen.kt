package gt.code.movieapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import gt.code.movieapp.models.MovieViewModel
import gt.code.movieapp.screensComposable.MovieList


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: MovieViewModel
) {
    // state variable to track whether the menu is expanded
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Movies")
                },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    // DropdownMenu for "Favorites"
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        DropdownMenuItem(onClick = {
                            navController.navigate(Screen.Favorites.route)
                            expanded = false
                        }) {
                            Text(text = "Favorites")
                        }
                        Divider() // Add a divider
                        DropdownMenuItem(onClick = {
                            navController.navigate(Screen.AddMovie.route) // Navigate to AddMovieScreen
                            expanded = false
                        }) {
                            Text(text = "Add Movie")
                        }
                    }
                }
            )
        }
    ) {
        // LazyColumn to display list of movies
        MovieList(movies = viewModel.movieList, onItemClick = { movieId ->
            navController.navigate(Screen.Details.passId(movieId))
        }
        ) { movieId -> viewModel.toggleFavorite(movieId) }
    }
}






