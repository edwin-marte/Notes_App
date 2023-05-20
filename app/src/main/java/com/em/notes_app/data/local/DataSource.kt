package com.em.notes_app.data.local

import com.em.notes_app.data.model.NoteEntity
import com.em.notes_app.core.Resource

interface DataSource {
    suspend fun getNotes(): Resource<List<NoteEntity>>
    suspend fun insertNoteIntoRoom(note: NoteEntity)
    suspend fun deleteNote(note: NoteEntity)
}