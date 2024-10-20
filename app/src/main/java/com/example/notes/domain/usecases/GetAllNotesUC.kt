package com.example.notes.domain.usecases

import com.example.notes.domain.repository.NoteManageRepository
import javax.inject.Inject

class GetAllNotesUC @Inject constructor(
    private val repository: NoteManageRepository
) {
    suspend operator fun invoke() = repository.getAllNotes()
}