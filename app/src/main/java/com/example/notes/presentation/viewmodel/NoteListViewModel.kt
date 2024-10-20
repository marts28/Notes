package com.example.notes.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.usecases.DeleteNote
import com.example.notes.domain.usecases.GetAllNotesUC
import com.example.notes.domain.usecases.SearchNoteUC
import com.example.notes.presentation.state.NoteListState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteListViewModel @Inject constructor(
    private val getAllNotesUC: GetAllNotesUC,
    private val deleteNoteUC: DeleteNote,
    private val searchNoteUC: SearchNoteUC
) : ViewModel() {

    private val _state = MutableStateFlow<NoteListState>(NoteListState.Loading)
    val state = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler{_, throwable ->
        Log.d("NoteListViewModel", "Exception: $throwable")
    }

    fun getNotes() {
        viewModelScope.launch(exceptionHandler){
            getAllNotesUC().collect {
                if (it.isEmpty())
                    _state.emit(NoteListState.EmptyNoteList)
                else
                    _state.emit(NoteListState.NoteList(noteList = it))
            }
        }
    }

    fun searchNote(query: String?) {
        query?.let { title ->
            viewModelScope.launch(exceptionHandler){
                searchNoteUC(title).collect {
                    if (it.isEmpty())
                        _state.emit(NoteListState.EmptyNoteList)
                    else
                        _state.emit(NoteListState.NoteList(noteList = it))
                }
            }
        }

    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch(exceptionHandler){
            deleteNoteUC(noteId)
        }
    }
}