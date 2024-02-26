package com.example.bd.data.database

import androidx.room.TypeConverter
import com.example.bd.domain.model.OptionList
import com.example.bd.domain.model.PointList
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun optionListToJson(optionList: OptionList): String? {
        return Gson().toJson(optionList)
    }

    @TypeConverter
    fun jsonToOptionList(json: String): OptionList? {
        return Gson().fromJson(json, OptionList::class.java)
    }

    @TypeConverter
    fun pointListToJson(pointList: PointList): String? {
        return Gson().toJson(pointList)
    }

    @TypeConverter
    fun jsonToPointList(json: String): PointList? {
        return Gson().fromJson(json, PointList::class.java)
    }
}