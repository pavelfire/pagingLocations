package com.example.android.codelabs.paging.experimental.room_experience.money_control

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoneyPlaceDao {

    @Insert suspend fun addMoneyPlace(place: MoneyPlace)
    @Update suspend fun updateMoneyPlace(place: MoneyPlace)
    @Delete suspend fun deleteMoneyPlace(place: MoneyPlace)

    @Query("SELECT * FROM MoneyPlaces")
    fun observeNotesByPlace(): Flow<List<MoneyPlace>>
}