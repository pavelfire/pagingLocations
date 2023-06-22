package com.example.android.codelabs.paging.experimental.room_experience

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Banks, Invest organizations where saved money */
@Entity(tableName = "MoneyPlaces")
data class MoneyPlace(
    @PrimaryKey
    val name: String,
    val address: String,
    val comment: String,
    val moneyNote: List<MoneyNote>
)
