package com.nat.winsome_assessment.application.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nat.winsome_assessment.screens.mainScreen.domain.models.MovieUiModel

class DatabaseTypeConverter {
    private var gson = Gson()
    @TypeConverter
    fun convertModelToString(model: MovieUiModel): String {
        return gson.toJson(model)
    }


    @TypeConverter
    fun convertModelFromString(model: String): MovieUiModel {
        val type = object : TypeToken<MovieUiModel>() {}.type
        return gson.fromJson(model, type)
    }

}