package gt.code.movieapp.screens


import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import gt.code.movieapp.models.MovieViewModel
import gt.code.movieapp.reusable.SimpleAppBar
import gt.code.movieapp.screensComposable.MovieList


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(navController: NavController, viewModel: MovieViewModel) {

    Scaffold(
        topBar = {
            SimpleAppBar(title = "Favorites", onBackClick = {
                navController.popBackStack()
            })
        }
    ) {
        // LazyColumn to display list of favorite movies
        MovieList(movies = viewModel.favoriteMovies, onItemClick = { movieId ->
            navController.navigate(Screen.Details.passId(movieId))
        }) { movie ->
            viewModel.toggleFavorite(movie)
        }
    }
        }




