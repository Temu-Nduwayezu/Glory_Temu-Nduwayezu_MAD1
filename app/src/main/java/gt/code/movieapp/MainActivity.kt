package gt.code.movieapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import gt.code.movieapp.models.Movie
import gt.code.movieapp.models.getMovies
import gt.code.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {

                        // Display the movie list
                        MovieList()
                    }
                }
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie = getMovies()[0]) {
    // state variable to track whether the arrow icon is toggled
    var arrowToggled by remember { mutableStateOf(false) }

    // A Card composable with rounded corners and elevation
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // A Box composable with the movie poster as the background image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
            ) {
                //Load the movie poster from the URL using Coil
                Image(
                    /*
                    // load image fom resources
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop
                     */
                    painter = rememberImagePainter(movie.images[0]),
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )

                // A Icon composable for marking favorite movies
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        tint = MaterialTheme.colors.secondary,
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            }

            // A Row with the movie title and an arrow button for showing/hiding details
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                )
                IconButton(onClick = { arrowToggled = !arrowToggled }) {
                    Icon(
                        imageVector = if (arrowToggled) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = "Details"
                    )
                }
            }
            // Shown or hidden based on the arrow button details

            // Details (Director, Year, Genre, Actors, Rating)
            if (arrowToggled) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 14.dp)
                        .animateContentSize()
                ) {
                    Text(
                        text = "Director: ${movie.director}",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = "Release Year: ${movie.year}",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = "Genre: ${movie.genre}",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = "Actors: ${movie.actors}",
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = "Rating: ${movie.rating}",
                        style = MaterialTheme.typography.caption,
                    )
                }

                // Movie Plot
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                ) {

                    Text(
                        text = "Plot: ${movie.plot}",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                }

            }
        }
    }
}

// Displaying a list of movies using a LazyColumn
@Preview(showBackground = true)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieList(movies: List<Movie> = getMovies()) {
    // state variable to track whether the menu is expanded
    var expanded by remember { mutableStateOf(false) }
 // design TopAppBar
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
                        DropdownMenuItem(onClick = { /* Handle favorites click */ }) {
                            Text(text = "Favorites")
                        }
                    }
                }
            )
        }
    ) {
        // LazyColumn to display list of movies
        LazyColumn {
            items(items = movies) { movie ->
                MovieRow(movie = movie)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieAppTheme {
        MovieList()
    }
}
