package gt.code.movieapp.screensComposable

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

import gt.code.movieapp.models.Movie
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MovieRow(movie: Movie, onMovieRowClick: (String) -> Unit, onFavoriteClick: (String) -> Unit) {
    // state variable to track whether the arrow icon is toggled
    var arrowToggled by remember { mutableStateOf(false) }
    val isFavorite by remember { mutableStateOf( movie.isFavorite) }

    // A Card composable with rounded corners and elevation
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onMovieRowClick(movie.id) }
        ,
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // A Box composable with the movie poster as the background image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)

            ) {
                ///Load the movie poster from the URL using Coil
                Image(
                    painter = rememberAsyncImagePainter(movie.images[0]),
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
                )
                {
                    IconButton(onClick = { onFavoriteClick(movie.id) })
                    {
                        Icon(
                            tint = MaterialTheme.colors.secondary,
                            imageVector = if(movie.isFavorite)Icons.Filled.Favorite
                            else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
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

            //Movie Details
            if (arrowToggled) {
                MovieDetails(movie = movie)

            }
        }
    }
}
//Movie  Details (Director, Year, Genre, Actors, Rating)
@Composable
fun MovieDetails(movie: Movie) {
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
// LazyColumn to display list of movies
@Composable
fun MovieList(movies: List<Movie>, onItemClick: (String) -> Unit, onFavoriteClick: (String) -> Unit) {
    LazyColumn {
        items(movies) { movie ->
            MovieRow(movie = movie,
                onMovieRowClick = { movieId -> onItemClick(movieId) },
                onFavoriteClick = { movieId -> onFavoriteClick(movieId) }
            )
        }
    }
}



//shows all movie images inside scrollable Card
@Composable
fun ImageRow(movie: Movie) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(movie.images) { image ->
            Card(
                shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                modifier = Modifier
                    .width(175.dp)
                    .padding(8.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current).data(data = image).apply(block = fun ImageRequest.Builder.() {
                            crossfade(true)
                        }).build()
                    ),
                    contentDescription = "Scene from ${movie.title}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                    ,
                    contentScale = ContentScale.FillHeight
                )
            }
        }
    }
}