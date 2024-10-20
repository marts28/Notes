package com.example.notes.data.repository

import com.example.notes.data.datasource.LocalDataSource
import com.example.notes.domain.entity.NoteEntity
import com.example.notes.domain.repository.NoteManageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NoteManageRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : NoteManageRepository {

    override suspend fun getAllNotes():Flow<List<NoteEntity>> {
        return flow{
            emitAll(localDataSource.getAllNotes())
        }
    }

    override suspend fun searchNote(query: String): Flow<List<NoteEntity>> {
        return flow{
            emitAll(localDataSource.searchNotes(query))
        }
    }

    override suspend fun getNote(id: Int): NoteEntity {
        return localDataSource.getNote(id)
    }

    override suspend fun saveNote(noteEntity: NoteEntity) {
        localDataSource.saveNote(noteEntity)
    }

    override suspend fun deleteNote(id: Int) {
        localDataSource.deleteNote(id)
    }
}