package com.example.notes.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [NoteDbModel::class], version = 1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNoteDao():NoteDao

    companion object{
        private const val DB_NAME = "notes.db"
        private var INSTANCE: NoteDatabase? = null
        private var LOCK = Any()

        fun getInstance(application: Application): NoteDatabase{
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(application, NoteDatabase::class.java, DB_NAME).build()
                INSTANCE = db
                return db
            }

        }

    }
}