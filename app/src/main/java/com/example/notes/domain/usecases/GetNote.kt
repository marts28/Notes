package com.example.notes.domain.usecases

import com.example.notes.domain.repository.NoteManageRepository
import javax.inject.Inject

class GetNote @Inject constructor(
    private val repository: NoteManageRepository
) {

    suspend operator fun invoke(id: Int) = repository.getNote(id)

}