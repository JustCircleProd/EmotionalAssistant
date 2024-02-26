package com.example.bd.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bd.data.database.dao.EmotionDao
import com.example.bd.data.database.dao.EmotionResultDao
import com.example.bd.data.database.dao.EmotionalStateDao
import com.example.bd.data.database.dao.EmotionalStateTestDao
import com.example.bd.data.database.dao.UserDao
import com.example.bd.domain.model.Emotion
import com.example.bd.domain.model.EmotionRecommendation
import com.example.bd.domain.model.EmotionResult
import com.example.bd.domain.model.EmotionalState
import com.example.bd.domain.model.EmotionalStateRecommendation
import com.example.bd.domain.model.EmotionalStateTest
import com.example.bd.domain.model.EmotionalStateTestQuestion
import com.example.bd.domain.model.EmotionalStateTestResult
import com.example.bd.domain.model.User

@Database(
    version = 9,
    entities = [
        User::class,
        EmotionalState::class, EmotionalStateTest::class, EmotionalStateTestQuestion::class
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun emotionalStateDao(): EmotionalStateDao
    abstract fun emotionalStateTestDao(): EmotionalStateTestDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}