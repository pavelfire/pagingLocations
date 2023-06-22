package com.example.android.codelabs.paging.experimental.room_experience.media_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracks",
    foreignKeys = [
        ForeignKey(
            entity = AlbumDb::class,
            parentColumns = ["album_id"],
            childColumns = ["album_id"],
        ),
    ]
)
data class TrackDb(
    @PrimaryKey
    @ColumnInfo(name = "track_id")
    val id: String,
    @ColumnInfo(name = "album_id")
    val albumId: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "duration")
    val durationMs: Long,

    )
