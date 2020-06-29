package com.litrud.dogsgallery

import android.app.Application
import com.litrud.dogsgallery.di.KoinLogger
import com.litrud.dogsgallery.di.mainModule
import com.litrud.dogsgallery.network.monitoring.NetworkStateHolder.registerConnectivityBroadcaster
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DogsGalleryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            logger(KoinLogger())
            androidContext(this@DogsGalleryApplication)
            modules(
                listOf(
                    mainModule
                )
            )
        }
        registerConnectivityBroadcaster()
    }
}