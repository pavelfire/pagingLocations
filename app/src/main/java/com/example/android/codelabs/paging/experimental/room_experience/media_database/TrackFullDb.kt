package com.example.android.codelabs.paging.experimental.room_experience.media_database

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TrackFullDb(
    @Embedded
    val info: TrackDb,

    @Relation(
        parentColumn = "track_id",
        entity = ArtistDb::class,
        entityColumn = "artist_id",
        associateBy = Junction(ArtistTrackCrossRef::class)
    )
    val artists: List<ArtistDb>
)


// хранение данных Яндекс
// https://www.youtube.com/watch?v=xG_HRAz6axU&list=PLQC2_0cDcSKAcQQjPdi77FUF8LYoLZHoO&index=17