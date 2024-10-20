package com.example.notes

import android.app.Application
import com.example.notes.di.component.DaggerApplicationComponent

class NoteApplication: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}