package com.movie.newflix

import android.app.Application
import com.movie.newflix.di.initKoin
import org.koin.android.ext.koin.androidContext

class NewFlixApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NewFlixApp)
        }
    }
}
