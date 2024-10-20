package com.example.notes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.notes.R
import com.example.notes.domain.entity.NoteEntity

class NoteListAdapter : ListAdapter<NoteEntity, NoteListItemViewHolder>(NoteListDiffUtilCallBack()) {

    var onNoteClickListener: ((NoteEntity) -> Unit)? = null
    var onNoteLongClickListener: ((NoteEntity) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteListItemViewHolder(view)
    }
    override fun onBindViewHolder(holder: NoteListItemViewHolder, position: Int) {
        val noteItem = getItem(position)
        holder.bind(noteItem)

        holder.itemView.setOnClickListener {
            onNoteClickListener?.invoke(noteItem)
        }

        holder.itemView.setOnLongClickListener {
            onNoteLongClickListener?.invoke(noteItem)
            true
        }
    }
}