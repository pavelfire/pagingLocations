package com.example.android.codelabs.paging.data.db.character

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.codelabs.paging.data.db.LocationsRemoteKeys
import com.example.android.codelabs.paging.data.db.LocationsRemoteKeysDao
import com.example.android.codelabs.paging.model.character.CharacterEntity

const val CHARACTERS_DB_NAME = "characters.db"

@Database(
    entities = [CharacterEntity::class, LocationsRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class CharactersDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun locationsRemoteKeysDao(): LocationsRemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: CharactersDatabase? = null

        fun getInstance(context: Context): CharactersDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CharactersDatabase::class.java,
                CHARACTERS_DB_NAME
            ).build()
    }
}