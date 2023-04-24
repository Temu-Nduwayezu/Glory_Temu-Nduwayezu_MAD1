package gt.code.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
    movieId: String?
) {


    movieId?.let {
        val movieDetailsViewModel: MovieDetailsViewModel = viewModel(
            factory = InjectorUtils.provideMovieDetailsViewModelFactory(
                LocalContext.current, movieId
            )
        )

        val movie = movieDetailsViewModel.movieListState.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`

        Scaffold(
            scaffoldState = scaffoldState, // attaching `scaffoldState` to the `Scaffold`
            topBar = {
                SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                    Text(text = movie.value.title)

                }
            },
        ) { padding ->
            MainContent(
                Modifier.padding(padding),
                movie.value,
                onFavClick = { movie ->
                    coroutineScope.launch {
                        movieDetailsViewModel.updateFavoriteMovies(movie)
                    }
                }
            )
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