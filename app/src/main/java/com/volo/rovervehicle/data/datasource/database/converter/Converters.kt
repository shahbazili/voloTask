package com.volo.rovervehicle.data.datasource.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.volo.rovervehicle.data.model.Camera

class Converters {
    @TypeConverter
    fun fromGroupTaskMemberList(value: List<Camera>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Camera>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGroupTaskMemberList(value: String): List<Camera> {
        val gson = Gson()
        val type = object : TypeToken<List<Camera>>() {}.type
        return gson.fromJson(value, type)
    }
}