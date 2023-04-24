package gt.code.movieapp.utils

import android.content.Context
import gt.code.movieapp.data.MovieDatabase
import gt.code.movieapp.repositories.MovieRepository
import gt.code.movieapp.viewmodels.AddMovieViewModelFactory
import gt.code.movieapp.viewmodels.FavoriteMoviesViewModelFactory
import gt.code.movieapp.viewmodels.HomeMoviesViewModelFactory
import gt.code.movieapp.viewmodels.MovieDetailsViewModelFactory

object InjectorUtils {
    private fun getMovieRepository(context: Context): MovieRepository {
        return MovieRepository(MovieDatabase.getDatabase(context).movieDao())
    }
    fun provideAddMovieViewModelFactory(context: Context): AddMovieViewModelFactory{
        val repository = getMovieRepository(context)
        return  AddMovieViewModelFactory(repository)
    }

    fun provideHomeMoviesViewModelFactory(context: Context): HomeMoviesViewModelFactory{
        val repository = getMovieRepository(context)
        return  HomeMoviesViewModelFactory(repository)
    }


    fun provideFavoriteMoviesViewModelFactory(context: Context): FavoriteMoviesViewModelFactory{
        val repository = getMovieRepository(context)
        return  FavoriteMoviesViewModelFactory(repository)
    }


    fun provideMovieDetailsViewModelFactory(context: Context, movieId: String): MovieDetailsViewModelFactory{
        val repository = getMovieRepository(context)
        return  MovieDetailsViewModelFactory(repository,movieId)
    }


}