package com.example.notes.presentation.state

import com.example.notes.domain.entity.NoteEntity

sealed class NoteListState{
    data object Loading: NoteListState()
    data class NoteList(val noteList: List<NoteEntity>) : NoteListState()
    data object EmptyNoteList: NoteListState()

}
