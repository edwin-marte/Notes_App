package com.example.notes_app.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName="noteEntity")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0,
    @ColumnInfo(name="note_title")
    val title: String = "",
    @ColumnInfo(name="note_description")
    val description: String = ""
): Parcelable
