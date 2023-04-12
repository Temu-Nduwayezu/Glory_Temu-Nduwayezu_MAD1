
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

    private val _errorTitle = mutableStateOf("")
    val errorTitle: State<String>
        get() = _errorTitle

    private val _errorYear = mutableStateOf("")
    val errorYear: State<String>
        get() = _errorYear

    private val _errorGenres = mutableStateOf("")
    val errorGenres: State<String>
        get() = _errorGenres

    private val _errorDirector = mutableStateOf("")
    val errorDirector: State<String>
        get() = _errorDirector

    private val _errorActors = mutableStateOf("")
    val errorActors: State<String>
        get() = _errorActors

    private val _errorRating = mutableStateOf("")
    val errorRating: State<String>
        get() = _errorRating

    private val _addEnabledSaveButton = mutableStateOf(false)
    val enabledSaveButton: State<Boolean>
        get() = _addEnabledSaveButton

    fun validateTitle(title: String) {
        _titleValid.value = title.isNotBlank()
        updateErrorTitle()
        validate()
    }

    fun validateYear(year: String) {
        _yearValid.value = year.isNotBlank() && year.toIntOrNull() != null
        updateErrorYear()
        validate()
    }

    fun validateGenres(genres: List<ListItemSelectable>) {
        _genresValid.value = genres.any { it.isSelected }
        updateErrorGenres()
        validate()
    }

    fun validateDirector(director: String) {
        _directorValid.value = director.isNotBlank()
        updateErrorDirector()
        validate()
    }

    fun validateActors(actors: String) {
        _actorsValid.value = actors.isNotBlank()
        updateErrorActors()
        validate()
    }

    fun validateRating(rating: String) {
        _ratingValid.value = rating.isNotBlank() && rating.toDoubleOrNull() != null && rating.toDouble().let { it in 0.0..10.0 }
        updateErrorRating()
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

        updateEnableSaveButton()
    }

    private fun updateEnableSaveButton() {
        _addEnabledSaveButton.value = _isValid.value
    }

    private fun updateErrorTitle() {
        _errorTitle.value = if (_titleValid.value) "" else "Title is required"
    }

    private fun updateErrorYear() {
        _errorYear.value = if (_yearValid.value) "" else "Year is required and must be integer"
    }

    private fun updateErrorGenres() {
        _errorGenres.value = if (_genresValid.value) "" else "At least one genre must be selected"
    }

    private fun updateErrorDirector() {
        _errorDirector.value = if (_directorValid.value) "" else "Director is required"
    }

    private fun updateErrorActors() {
        _errorActors.value = if (_actorsValid.value) "" else "Actors are required"
    }

    private fun updateErrorRating() {
        _errorRating.value = if (_ratingValid.value) "" else "Rating is required and must be between 0.0-10.0."
    }

    fun resetFields() {
        _titleValid.value = false
        _yearValid.value = false
        _genresValid.value = false
        _directorValid.value = false
        _actorsValid.value = false
        _ratingValid.value = false
        _plot.value = ""
        _errorTitle.value = ""
        _errorYear.value = ""
        _errorGenres.value = ""
        _errorDirector.value = ""
        _errorActors.value = ""
        _errorRating.value = ""
        _addEnabledSaveButton.value = false
    }

    fun addMovie(movie: Movie) {
        _movieList.add(movie)
        resetFields()
    }

    fun removeMovie(movieId: String) {
        _movieList.removeAll { it.id == movieId }
        _favoriteMovies.value = _favoriteMovies.value.filter { it.id != movieId }
    }

    fun toggleFavorite(movieId: Movie) {
        val movie = _movieList.find { it.id == movieId.id } ?: return
        val isFavorite = movie.isFavorite
        movie.isFavorite = !isFavorite
        if (isFavorite) {
            _favoriteMovies.value =
                _favoriteMovies.value.filter { it.id != movieId.id } // remove from favorites
        } else {
            _favoriteMovies.value = _favoriteMovies.value + movie // add to favorites
        }
    }
}

