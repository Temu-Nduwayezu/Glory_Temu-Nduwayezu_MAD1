package gt.code.movieapp.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gt.code.movieapp.repositories.MovieRepository

class HomeMoviesViewModelFactory(private val movieRepository: MovieRepository,private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HomeMoviesViewModel::class.java)) {

            return HomeMoviesViewModel(movieRepository, context ) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}