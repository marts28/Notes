package com.example.notes.domain.entity

import java.util.Calendar


data class NoteEntity(
    val id: Int,
    val title: String,
    val text: String,
    val editDate: Calendar
){
    companion object{
        const val NEW_NOTE_ID = 0
    }
}
