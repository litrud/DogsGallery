package com.litrud.dogsgallery.di

import android.util.Log
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE

class KoinLogger : Logger() {
    override fun log(level: Level, msg: MESSAGE) {
        when (level) {
            Level.DEBUG -> Log.d("KOINd", msg)
            Level.ERROR -> Log.e("KOINe", msg)
            Level.INFO -> Log.i("KOINi", msg)
            Level.NONE -> Log.v("KOINn", msg)
        }
    }
}