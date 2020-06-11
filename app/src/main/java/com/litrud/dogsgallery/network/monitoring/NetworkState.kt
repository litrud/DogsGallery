package com.litrud.dogsgallery.network.monitoring

import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities

interface NetworkState {
    val isConnected: Boolean
    val network: Network?
    val networkCapabilities: NetworkCapabilities?
    val linkProperties: LinkProperties?
}