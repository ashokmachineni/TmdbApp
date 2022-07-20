package com.android.movies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieKey")
data class MovieKey(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nextKey: Int
)