package com.example.android.codelabs.paging.experimental.room_experience.media_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistDb(
    @PrimaryKey
    @ColumnInfo(name = "artist_id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String
)
