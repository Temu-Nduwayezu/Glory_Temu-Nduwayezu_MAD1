package gt.code.movieapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import gt.code.movieapp.models.Movie
import gt.code.movieapp.models.MovieViewModel
import gt.code.movieapp.models.getMovies
import gt.code.movieapp.reusable.SimpleAppBar
import gt.code.movieapp.screensComposable.ImageRow
import gt.code.movieapp.screensComposable.MovieRow


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(navController: NavController, movieId: String?, viewModel: MovieViewModel) {
    val movie: Movie? = viewModel.movieList.find { it.id == movieId}
        //val movie = movies.find{ it.id == movieId}
    if (movie == null) {
        Text(text = "Movie not found")
        return
    }
    Scaffold(
        // Details (Director, Year, Genre, Actors, Rating)
        topBar = {
            SimpleAppBar(title = movie.title, onBackClick = {
                navController.popBackStack()
            })
        }
    )  {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            MovieRow(movie = movie, onMovieRowClick = {}, onFavoriteClick = { movie ->
                viewModel.toggleFavorite(movie) })

            // Image Row
            Text(
                text = "Movie Images",
                color = Color.Black,
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentSize()
            )
            ImageRow(movie = movie)
        }
    }
}




