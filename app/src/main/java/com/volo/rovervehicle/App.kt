package com.volo.rovervehicle

import android.annotation.SuppressLint
import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        var appContext: Application? = null
            private set
    }
}