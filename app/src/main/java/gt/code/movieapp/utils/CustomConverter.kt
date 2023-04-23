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
        val genres = mutableListOf<Genre>()

        value.split(",").map { it.trim() }.forEach {

            genres.add(enumValueOf(it))
        }

        return genres
    }

    // for Type Conversion of Images

    @TypeConverter
    fun toStringImage(value: List<String>?): String? {
        val images = value?.joinToString(separator = "#")
       // println("Images after conversion: $images")
        return images
    }
    @TypeConverter
    fun fromStringImage(value: String?): List<String>? {
        val images = value?.split("#")?.map { it.trim() }
       // println("Images before conversion: $images")
        if (images?.last() == "") {
            images.dropLast(1)
        }
        return images
    }

}


