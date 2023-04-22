package gt.code.movieapp.repositories

import gt.code.movieapp.data.MovieDao
import gt.code.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    suspend fun add(movie: Movie) = movieDao.addMovie(movie)

    suspend fun delete(movie: Movie) = movieDao.deleteMovie(movie)

    suspend fun update(movie: Movie) = movieDao.updateMovie(movie)

    fun  getAllMovies(): Flow<List<Movie>> = movieDao.getAllMovies()
    fun  getFavoriteMovies() : Flow<List<Movie>> = movieDao.getFavoriteMovies()

    fun getMovie(id: String): Flow<Movie> = movieDao.getMovie(id)

    fun deleteAll() = movieDao.deleteAll()
}