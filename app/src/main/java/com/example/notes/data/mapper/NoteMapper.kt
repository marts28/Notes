package com.example.notes.data.mapper

import com.example.notes.data.db.NoteDbModel
import com.example.notes.domain.entity.NoteEntity
import java.util.Calendar

fun NoteEntity.toDbModel():NoteDbModel = NoteDbModel(id, title, text, editDate.timeInMillis)

fun NoteDbModel.toEntity(): NoteEntity = NoteEntity(id, title, text, Calendar.getInstance().apply {
    this.timeInMillis = editDate
})

fun List<NoteDbModel>.toEntityList(): List<NoteEntity> = map { it.toEntity() }