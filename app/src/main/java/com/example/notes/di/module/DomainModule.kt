package com.example.notes.di.module

import com.example.notes.data.repository.NoteManageRepositoryImpl
import com.example.notes.di.scope.ApplicationScope
import com.example.notes.domain.repository.NoteManageRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    @ApplicationScope
    fun bindNoteManageRepository(impl: NoteManageRepositoryImpl): NoteManageRepository
}