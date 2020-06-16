package com.litrud.dogsgallery.network.monitoring

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities

internal class NetworkCallbackImpl(
    private val holder: NetworkStateImpl
) : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        holder.network = network
        holder.isConnected = true
    }
    override fun onLost(network: Network) {
        holder.network = network
        holder.isConnected = false
    }
    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        holder.network = network
        holder.networkCapabilities = networkCapabilities
    }
    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        holder.network = network
        holder.linkProperties = linkProperties
    }
}