package com.em.notes_app.data.local

import com.em.notes_app.data.model.NoteEntity
import com.em.notes_app.core.Resource
import javax.inject.Inject

class IDataSource @Inject constructor(private val appDatabase: AppDatabase): DataSource {
    override suspend fun getNotes(): Resource<List<NoteEntity>> {
        return Resource.Success(appDatabase.noteDao().getAllNotes())
    }

    override suspend fun insertNoteIntoRoom(note: NoteEntity) {
        appDatabase.noteDao().insertNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        appDatabase.noteDao().delete(note)
    }
}