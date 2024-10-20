package com.example.notes.di.module

import androidx.lifecycle.ViewModel
import com.example.notes.di.key.ViewModelKey
import com.example.notes.presentation.viewmodel.NoteListViewModel
import com.example.notes.presentation.viewmodel.NoteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(NoteListViewModel::class)
    fun bindNoteListViewModel(viewModel: NoteListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NoteViewModel::class)
    fun bindNoteViewModel(viewModel: NoteViewModel): ViewModel
}