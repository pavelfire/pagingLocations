package com.example.android.codelabs.paging.experimental.room_experience.media_database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        TrackDb::class,
        AlbumDb::class,
        ArtistDb::class,
        ArtistTrackCrossRef::class,
    ],
)
abstract class MusicCatalogStorage : RoomDatabase(){
    abstract fun tracks(): TrackDao
    abstract fun albums(): AlbumDao
    abstract fun artists(): ArtistDao
}