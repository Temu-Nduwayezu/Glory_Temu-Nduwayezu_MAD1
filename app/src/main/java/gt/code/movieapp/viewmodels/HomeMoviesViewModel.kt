package gt.code.movieapp.viewmodels


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gt.code.movieapp.data.MovieDatabase
import gt.code.movieapp.models.Movie
import gt.code.movieapp.models.getMovies
import gt.code.movieapp.repositories.MovieRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class HomeMoviesViewModel(private val movieRepository: MovieRepository,private val context: Context) : ViewModel() {
    private val _movieListState = MutableStateFlow(listOf<Movie>())
    val movieListState: StateFlow<List<Movie>> = _movieListState.asStateFlow()

    init {
        viewModelScope.launch {
           getMovies().forEach{ movie ->  movieRepository.add(movie)} // load movie into db (MarleneZoe Ruf idea)
            val movieDao = MovieDatabase.getDatabase(context).movieDao()
          //  movieDao.deleteAll()

            movieRepository.getAllMovies().collect() { movieList ->
                if (movieList.isNotEmpty()) {
                    _movieListState.value = movieList
                }
            }
        }
    }

    suspend fun updateFavoriteMovies(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        movieRepository.update(movie)
    }

}
