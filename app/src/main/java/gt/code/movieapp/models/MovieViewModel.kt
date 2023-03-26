package gt.code.movieapp.models
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel


class MovieViewModel : ViewModel() {

    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
        get() = _movieList

    private val _favoriteMovies = mutableStateOf(listOf<Movie>())
    val favoriteMovies: List<Movie>
        get() = _favoriteMovies.value

    private val _isValid = mutableStateOf(false)
    val isValid: State<Boolean>
        get() = _isValid

    private val _titleValid = mutableStateOf(false)
    val titleValid: State<Boolean>
        get() = _titleValid

    private val _yearValid = mutableStateOf(false)
    val yearValid: State<Boolean>
        get() = _yearValid

    private val _genresValid = mutableStateOf(false)
    val genresValid: State<Boolean>
        get() = _genresValid

    private val _directorValid = mutableStateOf(false)
    val directorValid: State<Boolean>
        get() = _directorValid

    private val _actorsValid = mutableStateOf(false)
    val actorsValid: State<Boolean>
        get() = _actorsValid

    private val _ratingValid = mutableStateOf(false)
    val ratingValid: State<Boolean>
        get() = _ratingValid

    private val _plot = mutableStateOf("")
    val plot: State<String>
        get() = _plot

    private val _errorText = mutableStateOf("")
    val errorText: State<String>
        get() = _errorText

    private val _addEnabledSaveButton = mutableStateOf(false)
    val enabledSaveButton: State<Boolean>
        get() = _addEnabledSaveButton

    fun validateTitle(title: String) {
        _titleValid.value = title.isNotBlank()
        validate()
    }

    fun validateYear(year: String) {
        _yearValid.value = year.isNotBlank() && year.toIntOrNull() != null
        validate()
    }

    fun validateGenres(genres: List<ListItemSelectable>) {
        _genresValid.value = genres.size >=1
        validate()
    }

    fun validateDirector(director: String) {
        _directorValid.value = director.isNotBlank()
        validate()
    }

    fun validateActors(actors: String) {
        _actorsValid.value = actors.isNotBlank()
        validate()
    }

    fun validateRating(rating: String) {
        _ratingValid.value = !rating.isNullOrBlank()
        validate()
    }
    fun validatePlot(plot: String) {
        _plot.value = plot
    }

    private fun validate() {
        _isValid.value = _titleValid.value &&
                _yearValid.value &&
                _genresValid.value &&
                _directorValid.value &&
                _actorsValid.value &&
                _ratingValid.value

        when {
            !_titleValid.value -> {
                _errorText.value = "Title is required"
            }
            !_yearValid.value -> {
                _errorText.value = "Year is required"
            }
            !_genresValid.value -> {
                _errorText.value = "At least one genre must be selected"
            }
            !_directorValid.value -> {
                _errorText.value = "Director is required"
            }
            !_actorsValid.value -> {
                _errorText.value = "Actors are required"
            }
            !_ratingValid.value -> {
                _errorText.value = "Rating is required"
            }
            else -> {
                _errorText.value = ""
            }
        }

        updateEnableSaveButton()
    }


    private fun updateEnableSaveButton() {
        _addEnabledSaveButton.value = _isValid.value
    }

    fun addMovie(movie: Movie){
        _movieList.add(movie)
    }


    fun removeMovie(movieId: String) {
        _movieList.removeAll { it.id == movieId }
        _favoriteMovies.value = _favoriteMovies.value.filter { it.id != movieId }
    }

    fun toggleFavorite(movieId: String) {
        val movie = _movieList.find { it.id == movieId } ?: return
        val isFavorite = movie.isFavorite
        movie.isFavorite = !isFavorite
        if (isFavorite) {
            _favoriteMovies.value =
                _favoriteMovies.value.filter { it.id != movieId }// remove from favorites
        } else {
            _favoriteMovies.value = _favoriteMovies.value + movie // add to favorites
        }
    }
}










