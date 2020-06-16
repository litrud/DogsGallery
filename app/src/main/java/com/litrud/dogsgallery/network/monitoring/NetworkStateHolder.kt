package com.litrud.dogsgallery.network.monitoring

import android.app.Application
import android.content.Context
import android.net.*

object NetworkStateHolder
    : NetworkState {

    private val holder = NetworkStateImpl

    override val isConnected: Boolean
        get() = holder.isConnected
    override val network: Network?
        get() = holder.network
    override val networkCapabilities: NetworkCapabilities?
        get() = holder.networkCapabilities
    override val linkProperties: LinkProperties?
        get() = holder.linkProperties

    fun Application.registerConnectivityBroadcaster() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                    // todo wtf
//                .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
//                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build(),
            NetworkCallbackImpl(holder)
        )
    }
}