package gt.code.movieapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import gt.code.movieapp.R
import gt.code.movieapp.models.Genre
import gt.code.movieapp.models.ListItemSelectable
import gt.code.movieapp.models.Movie
import gt.code.movieapp.models.MovieViewModel
import gt.code.movieapp.reusable.SimpleAppBar
import java.util.*

@Composable
fun AddMovieScreen(navController: NavController, viewModel: MovieViewModel) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SimpleAppBar(title = stringResource(id = R.string.add_movie), onBackClick = {
                navController.popBackStack()
            })
        }
    ){ padding ->
        MainContent(Modifier.padding(padding),
            viewModel = viewModel,
            navController = navController)
    }
    }


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(modifier: Modifier = Modifier, navController: NavController, viewModel: MovieViewModel) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {



        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {

            var title by remember {
                mutableStateOf("")
            }

            var year by remember {
                mutableStateOf("")
            }

            val genres = Genre.values().toList()

            var genreItems by remember {
                mutableStateOf(
                    genres.map { genre ->
                        ListItemSelectable(
                            title = genre.toString(),
                            isSelected = false
                        )
                    }
                )
            }

            var director by remember {
                mutableStateOf("")
            }

            var actors by remember {
                mutableStateOf("")
            }

            var plot by remember {
                mutableStateOf("")
            }

            var rating by remember {
                mutableStateOf("")
            }

            var isEnabledSaveButton by remember {
                mutableStateOf(false)
            }


            OutlinedTextField(
                value = title,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { title = it
                    viewModel.validateTitle(it)},
                label = { Text(text = stringResource(R.string.enter_movie_title)) },
                isError = !viewModel.titleValid.value
            )
            ErrorText(errorText = viewModel.errorTitle.value)

            OutlinedTextField(
                value = year,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { year = it
                    viewModel.validateYear(it)},
                label = { Text(stringResource(R.string.enter_movie_year)) },
                isError = !viewModel.yearValid.value
            )
            ErrorText(errorText = viewModel.errorYear.value)

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.select_genres),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6
                      )


            LazyHorizontalGrid(
                modifier = Modifier.height(100.dp),
                rows = GridCells.Fixed(3)
            ) {
                items(genreItems) { genreItem ->
                    Chip(
                        modifier = Modifier.padding(2.dp),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (genreItem.isSelected)
                                colorResource(id = R.color.purple_200)
                            else
                                colorResource(id = R.color.white)
                        ),
                        onClick = {
                            genreItems = genreItems.map {
                                if (it.title == genreItem.title) {
                                    genreItem.copy(isSelected = !genreItem.isSelected)
                                } else {
                                    it
                                }


                            }
                            viewModel.validateGenres(genreItems)

                        }


                    ) {
                        Text(text = genreItem.title)
                    }
                }

            }
            ErrorText(errorText = viewModel.errorGenres.value)



            OutlinedTextField(
                value = director,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { director = it
                    viewModel.validateDirector(it)},
                label = { Text(stringResource(R.string.enter_director)) },
                isError = !viewModel.directorValid.value
            )
            ErrorText(errorText = viewModel.errorDirector.value)




            OutlinedTextField(
                value = actors,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { actors = it
                    viewModel.validateActors(it)},
                label = { Text(stringResource(R.string.enter_actors)) },
               isError = !viewModel.actorsValid.value
            )
            ErrorText(errorText = viewModel.errorActors.value)

            OutlinedTextField(
                value = plot,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                onValueChange = { plot = it
                    viewModel.validatePlot(it)},
                label = {
                    Text(
                        textAlign = TextAlign.Start,
                        text = stringResource(R.string.enter_plot)
                    )
                },
                isError = false
            )

            OutlinedTextField(
                value = rating,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    rating = if(it.startsWith("0")) {
                        ""
                    } else {
                        it
                    }
                    viewModel.validateRating(it)

                },
                label = { Text(stringResource(R.string.enter_rating)) },
                isError = !viewModel.ratingValid.value
            )
            ErrorText(errorText = viewModel.errorRating.value)

            isEnabledSaveButton = viewModel.enabledSaveButton.value


            Button(
                enabled = isEnabledSaveButton,
                onClick = {
                    if (viewModel.isValid.value) {
                        val selectedGenres  = genreItems.filter { it.isSelected }.map { Genre.valueOf(it.title)}
                            val newMovie = Movie(
                            id = UUID.randomUUID().toString(),
                            title = title,
                            year = year,
                            genre = selectedGenres ,
                            director = director,
                            actors = actors,
                            plot = plot ?: "",
                            rating = rating.toFloat(),
                            images = listOf(),
                            initialIsFavorite = false
                        )
                        viewModel.addMovie(newMovie)
                       navController.popBackStack()// Navigate back to the previous screen
                    }
                },

            ) {
                Text(text = stringResource(R.string.add))
            }


        }
    }
}
@Composable
fun ErrorText(errorText: String) {
    if (errorText.isNotBlank()) {
        Text(
            text = errorText,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}


