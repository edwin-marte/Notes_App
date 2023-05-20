package com.em.notes_app.data.local

import androidx.room.*
import com.em.notes_app.data.model.NoteEntity

@Dao
interface NotesDao {
    @Query("SELECT * FROM noteEntity")
    suspend fun getAllNotes():List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)
}