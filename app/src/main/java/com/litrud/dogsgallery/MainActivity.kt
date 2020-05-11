package com.litrud.dogsgallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.litrud.dogsgallery.fragments.DogBreedsListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var fTransaction : FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        val fragment = DogBreedsListFragment.newInstance()

        fTransaction = supportFragmentManager.beginTransaction()
        fTransaction.add(R.id.container, fragment)
        fTransaction.addToBackStack(null)
        fTransaction.commit()
    }
}