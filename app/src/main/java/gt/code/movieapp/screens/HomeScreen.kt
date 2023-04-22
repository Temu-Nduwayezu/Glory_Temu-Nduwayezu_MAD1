package gt.code.movieapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import gt.code.movieapp.utils.InjectorUtils
import gt.code.movieapp.viewmodels.HomeMoviesViewModel
import gt.code.movieapp.widgets.HomeTopAppBar
import gt.code.movieapp.widgets.MovieRow
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
) {


    val homeMoviesViewModel: HomeMoviesViewModel = viewModel(
        factory = InjectorUtils.provideHomeMoviesViewModelFactory(LocalContext.current))


        Scaffold(topBar = {
            HomeTopAppBar(
                title = "Home",
                menuContent = {
                    DropdownMenuItem(onClick = { navController.navigate(Screen.AddMovieScreen.route) }) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Add Movie",
                                modifier = Modifier.padding(4.dp)
                            )
                            Text(
                                text = "Add Movie", modifier = Modifier
                                    .width(100.dp)
                                    .padding(4.dp)
                            )
                        }
                    }
                    DropdownMenuItem(onClick = { navController.navigate(Screen.FavoriteScreen.route) }) {
                        Row {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Favorites",
                                modifier = Modifier.padding(4.dp)
                            )
                            Text(
                                text = "Favorites", modifier = Modifier
                                    .width(100.dp)
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            )
        }) { padding ->
            MainContent(
                modifier = Modifier.padding(padding),
                navController = navController,
                viewModel = homeMoviesViewModel
            )
        }
    }

    @Composable
    fun MainContent(
        modifier: Modifier,
        navController: NavController,
        viewModel: HomeMoviesViewModel
    ) {
        MovieList(
            modifier = modifier,
            navController = navController,
            viewModel = viewModel
        )
    }



@Composable
fun MovieList(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeMoviesViewModel
) {
    val movieListState by viewModel.movieListState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        items(items = movieListState) { movieItem ->
            MovieRow(
                movie = movieItem,
                onMovieRowClick = { movieId ->
                    navController.navigate(Screen.DetailScreen.withId(movieId))
                },
                onFavClick  = { movie ->
                    coroutineScope.launch{
                    viewModel.updateFavoriteMovies(movie)
                    }
                }
            )
        }
    }
}


