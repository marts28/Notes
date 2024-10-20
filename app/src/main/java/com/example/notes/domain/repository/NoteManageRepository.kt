package com.example.notes.domain.repository

import com.example.notes.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteManageRepository {

    suspend fun getAllNotes():Flow<List<NoteEntity>>
    suspend fun searchNote(query: String):Flow<List<NoteEntity>>
    suspend fun getNote(id: Int): NoteEntity
    suspend fun saveNote(noteEntity: NoteEntity)
    suspend fun deleteNote(id: Int)
}