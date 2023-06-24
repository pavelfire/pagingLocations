package com.example.android.codelabs.paging.experimental.room_experience.media_database

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert suspend fun addAlbum(albumDb: AlbumDb)
    @Update suspend fun editAlbum(albumDb: AlbumDb)
    @Delete suspend fun removeAlbum(albumDb: AlbumDb)

    @Query("select * from albums")
    suspend fun allAlbums(): List<AlbumDb>

    @Query("select * from albums")
    fun observeAllAlbums(): Flow<List<AlbumDb>>

    @Query("select * from albums where track_id == :=id")
    fun findAlbum(id: String): AlbumDb

    @RawQuery
    fun findAlbums(query: SupportSQLiteQuery): AlbumDb
}