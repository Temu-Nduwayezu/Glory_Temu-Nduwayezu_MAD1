package gt.code.movieapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import gt.code.movieapp.utils.InjectorUtils
import gt.code.movieapp.viewmodels.FavoriteMoviesViewModel
import gt.code.movieapp.widgets.MovieRow
import gt.code.movieapp.widgets.SimpleTopAppBar
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(navController: NavController){
    val favoriteMoviesViewModel:FavoriteMoviesViewModel = viewModel(factory = InjectorUtils.provideFavoriteMoviesViewModelFactory(
        LocalContext.current))
    val favoriteMovieState by  favoriteMoviesViewModel.favoriteMoviesState.collectAsState()
    val coroutineScope = rememberCoroutineScope()


    Scaffold(topBar = {
        SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
            Text(text = "My Favorite Movies")
        }
    }){ padding ->
        Column(modifier = Modifier.padding(padding)) {
            LazyColumn {
                items(favoriteMovieState){ movie ->
                    MovieRow(
                        movie = movie,
                        onMovieRowClick = { movieId ->
                            navController.navigate(route = Screen.DetailScreen.withId(movieId))
                        },
                        onFavClick = { movie ->
                            coroutineScope.launch {
                                favoriteMoviesViewModel.updateFavoriteMovies(movie)
                            }
                        }
                    )
                }
            }
        }
    }
}