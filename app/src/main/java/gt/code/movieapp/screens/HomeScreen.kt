package gt.code.movieapp.screens

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import gt.code.movieapp.models.getMovies
import gt.code.movieapp.screensComposable.MovieRow


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    val movies = getMovies()
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
                    }
                }
            )
        }
    ) {
        // LazyColumn to display list of movies
        LazyColumn {
            items(movies) { movie ->
                MovieRow(movie) { movieId ->
                    navController.navigate("details_screen/${movieId}")
                }
            }
        }
    }
}




