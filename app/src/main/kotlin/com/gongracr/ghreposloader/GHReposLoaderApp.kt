package com.gongracr.ghreposloader

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GHReposLoaderApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Create an ImageLoader with optional configuration
        val imageLoader = ImageLoader.Builder(applicationContext)
            .logger(DebugLogger()) // Optional: Enable logging for debugging
            .crossfade(true) // Optional: Enable crossfade for smooth image transitions
            .placeholder(R.drawable.ic_default_user)
            .build()

        Coil.setImageLoader(imageLoader)
    }
}