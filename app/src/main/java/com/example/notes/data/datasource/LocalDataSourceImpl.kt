package com.example.notes.data.datasource

import com.example.notes.data.db.NoteDao
import com.example.notes.data.mapper.toDbModel
import com.example.notes.data.mapper.toEntity
import com.example.notes.data.mapper.toEntityList
import com.example.notes.domain.entity.NoteEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao
) : LocalDataSource {
    override fun getAllNotes() =
        noteDao.getAllNotes().map { noteList -> noteList.toEntityList() }

    override fun searchNotes(query: String) =
        noteDao.searchNote(query).map { noteList -> noteList.toEntityList() }

    override suspend fun getNote(noteId: Int) =
        noteDao.getNote(noteId).toEntity()

    override suspend fun saveNote(noteEntity: NoteEntity) {
        noteDao.insertNote(noteEntity.toDbModel())
    }


    override suspend fun deleteNote(noteId: Int) {
        noteDao.removeNote(noteId)
    }
}