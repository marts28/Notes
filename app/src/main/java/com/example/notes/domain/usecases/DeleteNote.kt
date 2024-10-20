package com.example.notes.domain.usecases

import com.example.notes.domain.repository.NoteManageRepository
import javax.inject.Inject

class DeleteNote @Inject constructor(
    private val repository: NoteManageRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteNote(id)
}