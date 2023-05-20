package com.em.notes_app.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.em.notes_app.R
import com.em.notes_app.core.BaseViewHolder
import com.em.notes_app.data.model.NoteEntity
import com.em.notes_app.databinding.NotesRecyclerviewRowBinding

class RecyclerViewAdapter(
    private val context: Context,
    private val notes: List<NoteEntity>,
    private val itemClickListener: OnItemClickListener,
    private val deleteClickListener: OnDeleteClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    lateinit var binding: NotesRecyclerviewRowBinding

    interface OnItemClickListener {
        fun onNoteClick(note: NoteEntity)
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(note: NoteEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.notes_recyclerview_row, parent, false
        )
        return MainViewHolder(binding.root)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(notes[position], position)
        }
    }

    inner class MainViewHolder(itemView: View) : BaseViewHolder<NoteEntity>(itemView) {
        override fun bind(item: NoteEntity, position: Int) {
            binding.noteTitle.text = item.title
            binding.noteDescription.text = item.description
            binding.deleteNoteButton.setOnClickListener { deleteClickListener.onDeleteClick(item) }
            itemView.setOnClickListener { itemClickListener.onNoteClick(item) }
        }
    }
}