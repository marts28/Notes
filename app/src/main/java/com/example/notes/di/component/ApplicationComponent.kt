package com.example.notes.di.component

import android.app.Application
import com.example.notes.di.scope.ApplicationScope
import com.example.notes.di.module.DataModule
import com.example.notes.di.module.DomainModule
import com.example.notes.di.module.PresentationModule
import com.example.notes.ui.fragment.NoteFragment
import com.example.notes.ui.fragment.NoteListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [PresentationModule::class, DataModule::class, DomainModule::class])
interface ApplicationComponent {

    fun inject(noteListFragment: NoteListFragment)
    fun inject(noteFragment: NoteFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}