package com.nickel.tallyscore.persistence.player

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PlayerTypeConverter {

    @TypeConverter
    fun fromScoresList(scores: List<Int>): String {
        return Gson().toJson(scores)
    }

    @TypeConverter
    fun toScoresList(scores: String): List<Int> {
        val listType = object: TypeToken<List<Int>>() {}.type
        return Gson().fromJson(scores, listType)
    }
}