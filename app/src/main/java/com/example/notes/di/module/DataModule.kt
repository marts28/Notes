package com.example.notes.di.module

import android.app.Application
import com.example.notes.data.datasource.LocalDataSource
import com.example.notes.data.datasource.LocalDataSourceImpl
import com.example.notes.data.db.NoteDao
import com.example.notes.data.db.NoteDatabase
import com.example.notes.di.scope.ApplicationScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

    companion object{
        @Provides
        @ApplicationScope
        fun provideNoteDao(application: Application): NoteDao{
            return NoteDatabase.getInstance(application).getNoteDao()
        }
    }
}