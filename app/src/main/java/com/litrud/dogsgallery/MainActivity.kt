package com.litrud.dogsgallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.litrud.dogsgallery.network.monitoring.NetworkEventsSnackbar

/**
 * An activity that inflates a layout that has a NavHostFragment.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NetworkEventsSnackbar(this).subscribe(false)
    }
}