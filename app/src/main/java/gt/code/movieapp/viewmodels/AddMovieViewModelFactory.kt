package gt.code.movieapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gt.code.movieapp.repositories.MovieRepository

class AddMovieViewModelFactory(private val movieRepository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AddMovieViewModel::class.java)) {

            return AddMovieViewModel(movieRepository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}