package com.example.android.codelabs.paging.experimental.room_experience.money_control

import androidx.room.*
import kotlinx.coroutines.flow.Flow

//@Dao
//interface MoneyNoteDao {
//
//    @Insert suspend fun addMoneyNote(moneyNote: MoneyNote)
//    @Update suspend fun updateMoneyNote(moneyNote: MoneyNote)
//    @Delete suspend fun deleteMoneyNote(moneyNote: MoneyNote)
//
//    @Query("SELECT * FROM MoneyNotes WHERE place == :id")
//    fun observeNotesByPlace(id: String): Flow<List<MoneyNote>>
//}