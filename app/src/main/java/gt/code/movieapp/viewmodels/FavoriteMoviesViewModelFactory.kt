package gt.code.movieapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gt.code.movieapp.repositories.MovieRepository

class FavoriteMoviesViewModelFactory(private val movieRepository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(FavoriteMoviesViewModel::class.java)) {

            return FavoriteMoviesViewModel(movieRepository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}