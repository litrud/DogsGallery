package com.litrud.dogsgallery.network.monitoring

import androidx.lifecycle.LiveData

object NetworkEvents : LiveData<Event>() {
    internal fun notify(event: Event) {
        postValue(event)
    }
}