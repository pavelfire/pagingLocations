package com.example.android.codelabs.paging.experimental.room_experience.media_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/** Связь многие ко многим **/
@Entity(
    tableName = "artist_tracks",
    primaryKeys = ["track_id", "artist_id"],
    foreignKeys = [
        ForeignKey(
            entity = TrackDb::class,
            parentColumns = ["track_id"],
            childColumns = ["track_id"],
        ),
        ForeignKey(
            entity = ArtistDb::class,
            parentColumns = ["artist_id"],
            childColumns = ["artist_id"],
        )
    ]
)
data class ArtistTrackCrossRef(
    @ColumnInfo(name = "track_id")
    val trackId: String,
    @ColumnInfo(name = "artist_id")
    val artistId: String
)

