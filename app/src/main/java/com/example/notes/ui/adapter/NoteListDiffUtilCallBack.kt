package com.example.notes.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.notes.domain.entity.NoteEntity

class NoteListDiffUtilCallBack: DiffUtil.ItemCallback<NoteEntity>() {
    override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity) =
        oldItem == newItem
}