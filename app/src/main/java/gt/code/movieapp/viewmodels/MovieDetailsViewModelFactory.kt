package gt.code.movieapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gt.code.movieapp.repositories.MovieRepository

class MovieDetailsViewModelFactory (private val movieRepository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {

            return MovieDetailsViewModel(movieRepository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}