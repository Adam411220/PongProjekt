package com.example.pong

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM scores")
    fun getAll() : List<Score>

    @Insert
    fun insertAll(vararg scores: Score)
}