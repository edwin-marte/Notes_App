package com.example.notes_app.application.injection

import android.content.Context
import androidx.room.Room
import com.example.notes_app.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "note_database"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideNoteDao(db: AppDatabase) = db.noteDao()
}