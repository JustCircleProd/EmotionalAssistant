package com.example.bd.core.data.db

import androidx.room.TypeConverter
import com.example.bd.core.domain.models.OptionList
import com.example.bd.core.domain.models.PointList
import com.google.gson.Gson
import java.time.LocalDate

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

    @TypeConverter
    fun localDateToString(localDate: LocalDate): String {
        return localDate.toString()
    }

    @TypeConverter
    fun localDateFromString(localDateStr: String): LocalDate {
        return LocalDate.parse(localDateStr)
    }
}