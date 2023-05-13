package com.example.notes_app.domain

import com.example.notes_app.data.model.NoteEntity
import com.example.notes_app.core.Resource

interface Repository {
    suspend fun getNotes(): Resource<List<NoteEntity>>
    suspend fun insertNote(note:NoteEntity)
    suspend fun deleteNote(note:NoteEntity)
}