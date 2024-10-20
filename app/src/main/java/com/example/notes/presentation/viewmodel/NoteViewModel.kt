package com.example.notes.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.domain.entity.NoteEntity
import com.example.notes.domain.usecases.DeleteNote
import com.example.notes.domain.usecases.GetNote
import com.example.notes.domain.usecases.SaveNote
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val saveNoteUC: SaveNote,
    private val getNoteUC: GetNote,
    private val deleteNoteUC: DeleteNote
):ViewModel() {

    private var _selectedNote = MutableLiveData<NoteEntity>()
    val selectedNote: LiveData<NoteEntity> = _selectedNote

    private var noteid = 0

    private val exceptionHandler = CoroutineExceptionHandler{_, throwable ->
        Log.d("NoteViewModel", "Exception: $throwable")
    }

    fun getSelectedNote(id: Int){
        if (id == 0) return
        noteid = id
        viewModelScope.launch(exceptionHandler){
            _selectedNote.value = (getNoteUC(id))
        }
    }

    fun saveNote(title: String, content: String){
        if (title.isEmpty() && content.isEmpty())
            return

        val editDate =
            if (content == selectedNote.value?.text && title == selectedNote.value?.title)
                selectedNote.value!!.editDate
        else
                Calendar.getInstance()
        val note = NoteEntity(
            noteid,
            title,
            content,
            editDate
        )

        viewModelScope.launch(exceptionHandler){
            saveNoteUC(note)
        }
    }

    fun deleteNote(noteId: Int){
        viewModelScope.launch(exceptionHandler){
            deleteNoteUC(noteId)
        }
    }



}