package com.example.notes.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Note")
data class NoteDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val text: String,
    val editDate: Long
)