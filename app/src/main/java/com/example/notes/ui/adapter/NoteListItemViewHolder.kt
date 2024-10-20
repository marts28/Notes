package com.example.notes.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ItemNoteBinding
import com.example.notes.domain.entity.NoteEntity
import com.example.notes.util.DateUtil

class NoteListItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemNoteBinding.bind(view)

    fun bind(noteItem: NoteEntity) = with(binding){
        if (noteItem.title.isEmpty())
            tvTitle.visibility = View.GONE
        else
            tvTitle.text = noteItem.title
        if (noteItem.text.isEmpty())
            tvContent.visibility = View.GONE
        else
            tvContent.text = noteItem.text
        val date = noteItem.editDate.time
        tvEditDate.text = DateUtil.formatDate(date)
    }
}