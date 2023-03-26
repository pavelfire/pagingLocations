package com.example.android.codelabs.paging.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

const val LOC_REMOTE_KEYS_TABLE_NAME = "loc_remote_keys"

@Entity(tableName = LOC_REMOTE_KEYS_TABLE_NAME)
data class LocationsRemoteKeys(
    @PrimaryKey
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)