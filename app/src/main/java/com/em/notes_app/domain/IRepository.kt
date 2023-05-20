package com.em.notes_app.domain

import com.em.notes_app.data.model.NoteEntity
import com.em.notes_app.core.Resource
import com.em.notes_app.data.local.DataSource
import javax.inject.Inject

class IRepository @Inject constructor(private val dataSource: DataSource): Repository {
    override suspend fun getNotes(): Resource<List<NoteEntity>> {
        return dataSource.getNotes()
    }

    override suspend fun insertNote(note: NoteEntity) {
        dataSource.insertNoteIntoRoom(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        dataSource.deleteNote(note)
    }
}