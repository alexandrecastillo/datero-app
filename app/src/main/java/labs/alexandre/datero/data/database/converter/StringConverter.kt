package labs.alexandre.datero.data.database.converter

import androidx.room.TypeConverter

class StringConverter {

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString("|")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return value.split("|")
    }

}