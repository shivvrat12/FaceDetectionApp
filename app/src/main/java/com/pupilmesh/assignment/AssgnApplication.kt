package com.pupilmesh.assignment

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import dagger.hilt.android.HiltAndroidApp
import java.io.File

@HiltAndroidApp
class AssgnApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val imageLoader = ImageLoader.Builder(this)
            .diskCache {
                DiskCache.Builder()
                    .directory(File(cacheDir, "cached_images"))
                    .maxSizeBytes(100L * 1024 * 1024)
                    .build()
            }
            .crossfade(true)
            .respectCacheHeaders(false) // just to be safe
            .build()

        Coil.setImageLoader(imageLoader)
    }
}