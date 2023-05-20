package com.em.notes_app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.em.notes_app.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NotesDao
}