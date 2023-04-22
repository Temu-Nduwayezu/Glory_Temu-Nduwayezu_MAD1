package gt.code.movieapp.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gt.code.movieapp.models.Movie
import gt.code.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(private val movieRepository: MovieRepository): ViewModel() {
    private val _favoriteListState = MutableStateFlow(listOf<Movie>())
    val favoriteMoviesState: StateFlow<List<Movie>> = _favoriteListState.asStateFlow()


    init {
        viewModelScope.launch {
            movieRepository.getFavoriteMovies().collect() { favoriteMovieList ->
                    _favoriteListState.value = favoriteMovieList
                }
            }
        }

    suspend fun updateFavoriteMovies(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        movieRepository.update(movie)
    }
}