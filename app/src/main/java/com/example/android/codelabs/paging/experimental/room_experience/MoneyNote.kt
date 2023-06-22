package com.example.android.codelabs.paging.experimental.room_experience

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** One note about money at one moment */
@Entity(tableName = "MoneyNotes")
data class MoneyNote(
    @PrimaryKey
    @ColumnInfo(name = "note_id")
    val id: String,
    @ColumnInfo(name = "date")
    val date: String,
    val amount: Int,
    val course: Int,
)
