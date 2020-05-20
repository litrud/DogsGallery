package com.litrud.dogsgallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/* todo wonders whether !
    after "terrier fox" breeds are not loaded into the list.
    !!! see PhotoListViewModel on line 27. list size = 1000 on request "terrier" breed
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