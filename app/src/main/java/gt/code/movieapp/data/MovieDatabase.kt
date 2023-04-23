package gt.code.movieapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import gt.code.movieapp.models.Movie
import gt.code.movieapp.utils.CustomConverter



// data base require 3 attributes
@Database(
    entities = [Movie::class], // table you want to be created
    version = 3,  // for changes
    exportSchema = false // can also set to true
)

@TypeConverters(CustomConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    // depends on number of entity you have, all  addressed here.
    abstract  fun movieDao() : MovieDao

    // create an object with singleton button we  don't want more than one instance of our DB
    // Singleton implementation of MovieDatabase
    companion object {
        @Volatile
        private var Instance: MovieDatabase? = null

        // threadsafe
        fun getDatabase(context: Context): MovieDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        Instance = it
                    }

            }
        }
    }
}