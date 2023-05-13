package com.example.notes_app.application.injection

import com.example.notes_app.data.local.IDataSource
import com.example.notes_app.data.local.DataSource
import com.example.notes_app.domain.IRepository
import com.example.notes_app.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {
    @Binds
    abstract fun bindIRepository(iRepository: IRepository): Repository

    @Binds
    abstract fun bindIDatasource(iDataSource: IDataSource): DataSource
}