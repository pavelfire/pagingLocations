package com.example.android.codelabs.paging.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.codelabs.paging.model.location.LOCATIONS_TABLE_NAME
import com.example.android.codelabs.paging.model.location.LocationEntity

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationEntity>)

    @Query(
        "SELECT * FROM $LOCATIONS_TABLE_NAME WHERE " +
                "name LIKE :queryString " //+
                //"ORDER BY name ASC"
    )
    fun locationsByName(queryString: String): PagingSource<Int, LocationEntity>

    @Query("DELETE FROM locations")
    suspend fun clearLocations()
}


