package com.example.notes.domain.usecases

import com.example.notes.domain.entity.NoteEntity
import com.example.notes.domain.repository.NoteManageRepository
import javax.inject.Inject

class SaveNote @Inject constructor(
    private val repository: NoteManageRepository
) {

    operator suspend fun invoke(noteEntity: NoteEntity) = repository.saveNote(noteEntity)
}