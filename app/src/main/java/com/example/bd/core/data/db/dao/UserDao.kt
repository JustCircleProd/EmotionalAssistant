package com.example.bd.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bd.core.domain.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun get(): User

    @Query("SELECT * FROM user")
    fun getFlow(): Flow<User>

    @Query("SELECT (SELECT COUNT(*) FROM user) != 0")
    fun isNotEmpty(): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
}