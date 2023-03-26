package gt.code.movieapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import gt.code.movieapp.models.Movie
import gt.code.movieapp.models.getMovies
import gt.code.movieapp.reusable.SimpleAppBar
import gt.code.movieapp.screensComposable.ImageRow
import gt.code.movieapp.screensComposable.MovieRow


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailScreen(navController: NavController, movieId: String?) {
    val movies =getMovies()
    val movie = movies.find{ it.id == movieId}
    if (movie == null) {
        Text(text = "Movie not found")
        return
    }
    Scaffold(
        // Details TopAppBar
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
            MovieRow(movie = movie)

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



