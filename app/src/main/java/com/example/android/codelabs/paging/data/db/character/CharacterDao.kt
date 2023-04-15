package com.example.android.codelabs.paging.data.db.character

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.codelabs.paging.model.character.CHARACTERS_TABLE_NAME
import com.example.android.codelabs.paging.model.character.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM $CHARACTERS_TABLE_NAME WHERE name LIKE :queryString")
    fun charactersByName(queryString: String): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM $CHARACTERS_TABLE_NAME")
    suspend fun clearCharacters()

}