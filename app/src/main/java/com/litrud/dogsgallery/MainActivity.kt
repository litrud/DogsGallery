package com.litrud.dogsgallery

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.litrud.dogsgallery.network.monitoring.Event
import com.litrud.dogsgallery.network.monitoring.NetworkEvents

/**
 * An activity that inflates a layout that has a NavHostFragment.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}