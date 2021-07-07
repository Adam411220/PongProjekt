package com.example.pong

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
class Score(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "number") val score: Int?
) {
}