package gt.code.movieapp.screensComposable

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

import gt.code.movieapp.models.Movie


@Composable
fun MovieRow(movie: Movie, onItemClick : (String)  -> Unit   = {}) {
    // state variable to track whether the arrow icon is toggled
    var arrowToggled by remember { mutableStateOf(false) }

    // A Card composable with rounded corners and elevation
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onItemClick(movie.id) }
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
                //    .clickable { onItemClick(movie.id) },
            ) {
                ///Load the movie poster from the URL using Coil
                Image(
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
                    painter = rememberImagePainter(
                        data = image,
                        builder = {
                            crossfade(true)
                        }
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
