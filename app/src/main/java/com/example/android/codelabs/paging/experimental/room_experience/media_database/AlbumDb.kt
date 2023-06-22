package com.example.android.codelabs.paging.experimental.room_experience.media_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumDb(
    @PrimaryKey
    @ColumnInfo(name = "album_id")
    val id: String,
    @ColumnInfo(name = "title")
    val title: String
)
