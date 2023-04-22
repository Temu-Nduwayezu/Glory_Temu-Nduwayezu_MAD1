package gt.code.movieapp.data

import androidx.room.*
import gt.code.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    //CRUD

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Query("SELECT * from movie")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("SELECT * from movie where id=:movieId")
    fun getMovie(movieId: String): Flow<Movie>

    @Query("SELECT * from movie where isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<Movie>>

    @Query("DELETE from movie")
    fun deleteAll()
}