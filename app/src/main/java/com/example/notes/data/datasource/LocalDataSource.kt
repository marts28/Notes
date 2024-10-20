package com.example.notes.data.datasource

import com.example.notes.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getAllNotes(): Flow<List<NoteEntity>>
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    suspend fun getNote(noteId: Int): NoteEntity

    suspend fun saveNote(noteEntity: NoteEntity)

    suspend fun deleteNote(noteId: Int)

}