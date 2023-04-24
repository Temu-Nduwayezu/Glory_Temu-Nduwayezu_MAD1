package gt.code.movieapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gt.code.movieapp.models.Movie
import gt.code.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch



class MovieDetailsViewModel(private val movieRepository:MovieRepository, private val id: String) : ViewModel() {
   val movieListState =MutableStateFlow(Movie())

    init {
        viewModelScope.launch {
            movieRepository.getMovie(id)
                .collect { movieList ->
                movieList?.let { // ? safe  call non null
                    movieListState.value = movieList
                }
            }
        }
    }

    suspend fun updateFavoriteMovies(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        movieRepository.update(movie)
    }


 /*   fun getMovie(movieId: String): Movie? {
        return movieListState.value.firstOrNull {
            it.id == movieId
        }
    }

  */
}





