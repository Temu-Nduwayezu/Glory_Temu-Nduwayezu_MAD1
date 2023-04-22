package gt.code.movieapp.utils

import androidx.room.TypeConverter
import gt.code.movieapp.models.Genre

class CustomConverter {


    @TypeConverter
    fun toStringGenre(value: List<Genre>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun fromStringGenre(value: String): List<Genre> {
        val genresList = mutableListOf<Genre>()

        value.split(",").map { it.trim() }.forEach {
            genresList.add(enumValueOf(it))
        }

        return genresList
    }

    // for Type Conversion of Images
    @TypeConverter

    fun fromStringImage(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun toStringImage(value: List<String>): String {
        return value.joinToString(separator = ",")
    }
}


