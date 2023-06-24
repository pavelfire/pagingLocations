package com.example.android.codelabs.paging.experimental.room_experience.media_database

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Insert suspend fun addTrack(track: TrackDb)
    @Update suspend fun editTrack(track: TrackDb)
    @Delete suspend fun removeTrack(track: TrackDb)

    @Query("select * from tracks")
    suspend fun all(): List<TrackFullDb>

    @Query("select * from tracks")
    fun observeAll(): Flow<List<TrackFullDb>>

    @Query("select * from tracks where track_id == :=id")
    fun findTrack(id: String): TrackFullDb

    @RawQuery
    fun findTrack(query: SupportSQLiteQuery): TrackDb
}