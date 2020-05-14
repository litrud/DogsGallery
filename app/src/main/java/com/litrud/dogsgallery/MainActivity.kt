package com.litrud.dogsgallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.litrud.dogsgallery.fragments.BreedListFragment
import com.litrud.dogsgallery.fragments.PhotoFragment
import com.litrud.dogsgallery.fragments.PhotoListFragment

class MainActivity : AppCompatActivity(),
    BreedListFragment.OpenListener,
    PhotoListFragment.OpenListener {

    private lateinit var fTransaction: FragmentTransaction
    private lateinit var fragmentBreedList: BreedListFragment
    private lateinit var fragmentPhotoList: PhotoListFragment
    private lateinit var fragmentPhoto: PhotoFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openBreedList()
    }

    private fun openBreedList() {
        fragmentBreedList = BreedListFragment.getInstance(this)
        fTransaction = supportFragmentManager.beginTransaction()
        fTransaction.add(R.id.container, fragmentBreedList)
        fTransaction.addToBackStack(null)
        fTransaction.commit()
    }

    override fun openPhotosList(breed_hyphenated: String, breed_keyword: String) {
        fragmentPhotoList = PhotoListFragment.getInstance(breed_hyphenated, breed_keyword, this)
        fTransaction = supportFragmentManager.beginTransaction()
        fTransaction.add(R.id.container, fragmentPhotoList)
        fTransaction.addToBackStack(null)
        fTransaction.commit()
    }

    override fun openPhoto(url: String) {
        fragmentPhoto = PhotoFragment.getInstance(url)
        fTransaction = supportFragmentManager.beginTransaction()
        fTransaction.add(R.id.container, fragmentPhoto)
        fTransaction.addToBackStack(null)
        fTransaction.commit()
    }
}