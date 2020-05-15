package com.litrud.dogsgallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/* todo
    customize the look of RecycledView and individual photo
    ( see glide docs https://github.com/bumptech/glide/wiki )

    use loading indicator and empty-message
 */

/**
 * An activity that inflates a layout that has a NavHostFragment.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}