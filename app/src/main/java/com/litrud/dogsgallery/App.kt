package com.litrud.dogsgallery

import android.app.Application
import com.litrud.dogsgallery.di.KoinLogger
import com.litrud.dogsgallery.di.mainModule
import com.litrud.dogsgallery.network.monitoring.NetworkStateHolder.registerConnectivityBroadcaster
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/*  todo :
     https://github.com/JakeWharton/picasso2-okhttp3-downloader
     https://square.github.io/okhttp/
     */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            logger(KoinLogger())
            androidContext(this@App)
            modules(
                listOf(
                    mainModule
                )
            )
        }
        registerConnectivityBroadcaster()

        // TODO ***
        val builder = Picasso.Builder(this)
//        builder.downloader(OkHttp3Downloader(this, Long.MAX_VALUE))   // todo : look up
        val built = builder.build()
        with(built) {
            setIndicatorsEnabled(true)
            isLoggingEnabled = true
        }
        Picasso.setSingletonInstance(built)
    }
}