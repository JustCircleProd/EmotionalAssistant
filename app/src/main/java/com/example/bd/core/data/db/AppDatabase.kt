package com.example.bd.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bd.core.data.db.dao.EmotionDao
import com.example.bd.core.data.db.dao.EmotionResultDao
import com.example.bd.core.data.db.dao.EmotionResultWithEmotionDao
import com.example.bd.core.data.db.dao.EmotionalStateDao
import com.example.bd.core.data.db.dao.EmotionalStateTestDao
import com.example.bd.core.data.db.dao.UserDao
import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.domain.models.EmotionResult
import com.example.bd.core.domain.models.EmotionalState
import com.example.bd.core.domain.models.EmotionalStateTest
import com.example.bd.core.domain.models.EmotionalStateTestQuestion
import com.example.bd.core.domain.models.User

@Database(
    version = 19,
    entities = [
        User::class,
        EmotionalState::class, EmotionalStateTest::class, EmotionalStateTestQuestion::class,
        Emotion::class, EmotionResult::class
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun emotionDao(): EmotionDao
    abstract fun emotionResultDao(): EmotionResultDao
    abstract fun emotionalStateDao(): EmotionalStateDao
    abstract fun emotionalStateTestDao(): EmotionalStateTestDao
    abstract fun emotionResultWithEmotionDao(): EmotionResultWithEmotionDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}