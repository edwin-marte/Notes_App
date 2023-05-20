package com.em.notes_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.em.notes_app.data.model.NoteEntity
import com.em.notes_app.domain.Repository
import com.em.notes_app.core.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val repository: Repository): ViewModel() {
    fun saveNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun getNotes() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repository.getNotes())
        } catch (e: java.lang.Exception) {
            emit(Resource.Failure(e))
        }
    }
}