package com.example.android.codelabs.paging.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<LocationsRemoteKeys>)

    @Query("SELECT * FROM $LOC_REMOTE_KEYS_TABLE_NAME WHERE id = :id")
    suspend fun remoteKeysLocId(id: Int): LocationsRemoteKeys?

    @Query("DELETE FROM $LOC_REMOTE_KEYS_TABLE_NAME")
    suspend fun clearRemoteKeys()
}