package com.example.notes.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("select * from Note ")
    fun getAllNotes():Flow<List<NoteDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteDbModel: NoteDbModel)

    @Query("delete from Note where id=:id")
    suspend fun removeNote(id: Int): Int

    @Query("select * from Note where id=:id")
    suspend fun getNote(id: Int): NoteDbModel

    @Query("select * from Note where title like '%' || :title || '%'")
    fun searchNote(title: String): Flow<List<NoteDbModel>>
}