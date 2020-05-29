package com.litrud.dogsgallery.listphoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.litrud.dogsgallery.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PhotoFragment : Fragment() {
    private lateinit var myActivity: AppCompatActivity
    private val viewModel: PhotosViewModel by sharedViewModel()
    private lateinit var viewPager2: ViewPager2
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        arguments?.let {
            position = PhotoFragmentArgs.fromBundle(it).position
        }

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_photo, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = ""
        myActivity = (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_red)
        }

        viewPager2 = view.findViewById(R.id.viewPager2_photos)
        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = viewModel.getItemCount()
            override fun createFragment(position: Int) =
                PhotoPage.newInstance(viewModel.getUrl(position))
        }
        viewPager2.setCurrentItem(position, false)

        return view
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> myActivity.onBackPressed()
        }
        return false
    }
}