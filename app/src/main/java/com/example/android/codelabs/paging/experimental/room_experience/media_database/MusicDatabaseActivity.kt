package com.example.android.codelabs.paging.experimental.room_experience.media_database

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.codelabs.paging.databinding.ActivityMainBinding

class MusicDatabaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val db = Room.databaseBuilder(
            applicationContext,
            MusicCatalogStorage::class.java,
            "music.db"
        )
            //.addTypeConverter(CustomTypeConverter)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                }
            })
            //.allowMainThreadQueries()
            .build()


    }
}