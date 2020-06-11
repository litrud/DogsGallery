package com.litrud.dogsgallery.network.monitoring

import android.net.LinkProperties
import android.net.NetworkCapabilities

sealed class Event {

    val networkState: NetworkState = NetworkStateHolder

    object ConnectivityLost : Event()
    object ConnectivityAvailable : Event()
    data class NetworkCapabilityChanged(val old: NetworkCapabilities?) : Event()
    data class LinkPropertyChanged(val old: LinkProperties?) : Event()
}