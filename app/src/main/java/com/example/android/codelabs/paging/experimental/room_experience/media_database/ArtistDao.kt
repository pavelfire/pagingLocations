package com.example.android.codelabs.paging.experimental.room_experience.media_database

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {

    @Insert suspend fun addArtist(artistDb: ArtistDb)
    @Update suspend fun editArtist(artistDb: ArtistDb)
    @Delete suspend fun removeArtist(artistDb: ArtistDb)

    @Query("select * from artists")
    suspend fun allArtist(): List<ArtistDb>

    @Query("select * from artists")
    fun observeAllArtist(): Flow<List<ArtistDb>>

    @Query("select * from artists where artist_id == :=id")
    fun findArtist(id: String): ArtistDb

    @RawQuery
    fun findArtist(query: SupportSQLiteQuery): ArtistDb
}