package gt.code.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import gt.code.movieapp.models.Movie
import gt.code.movieapp.utils.InjectorUtils
import gt.code.movieapp.viewmodels.MovieDetailsViewModel
import gt.code.movieapp.widgets.HorizontalScrollableImageView
import gt.code.movieapp.widgets.MovieRow
import gt.code.movieapp.widgets.SimpleTopAppBar
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    navController: NavController,
    movieId:String?){
    val movieDetailsViewModel : MovieDetailsViewModel = viewModel(factory = InjectorUtils.provideMovieDetailsViewModelFactory(
        LocalContext.current))
    val coroutineScope = rememberCoroutineScope()

   movieId?.let {
        val movie =  movieDetailsViewModel.getMovie(it)
        val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`

        Scaffold(scaffoldState = scaffoldState, // attaching `scaffoldState` to the `Scaffold`
            topBar = {
                SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                    if (movie != null) {
                        Text(text = movie.title)
                    }
                }
            },
        ) { padding ->
            movie?.let { it1 ->
                MainContent(
                    Modifier.padding(padding),
                    it1,
                    onFavClick = { movie ->
                        coroutineScope.launch {
                            movieDetailsViewModel.updateFavoriteMovies(movie)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    movie: Movie,
    onFavClick: (Movie) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            MovieRow(
                movie = movie,
                onFavClick = { movie ->
                    onFavClick(movie)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Divider()

            Text(text = "Movie Images", style = MaterialTheme.typography.h5)

            HorizontalScrollableImageView(movie = movie)
        }
    }
}