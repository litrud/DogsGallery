package com.litrud.dogsgallery.listphoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.litrud.dogsgallery.R
import com.litrud.dogsgallery.di.sharedGraphViewModel
import com.litrud.dogsgallery.network.monitoring.Event
import com.litrud.dogsgallery.network.monitoring.NetworkEvents
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PhotoFragment : Fragment() {
    private lateinit var myActivity: AppCompatActivity
    private val viewModel: PhotosViewModel by sharedGraphViewModel(R.id.photo_gallery)
    private lateinit var mAdapter: FragmentStateAdapter
    private var positionInit: Int = 0
    private var positionCurrent: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        arguments?.let {
            positionInit = PhotoFragmentArgs.fromBundle(it).position
            positionCurrent = positionInit
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

        mAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = viewModel.getItemCount()
            override fun createFragment(position: Int): Fragment {
                positionCurrent = position
                return PhotoPage.newInstance(viewModel.getUrl(position))
            }
        }

        // ViewPager2
        with(
            view.findViewById<ViewPager2>(R.id.viewPager2_photos)
        ) {
            adapter = mAdapter
            setCurrentItem(positionInit, false)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToNetworkEvents()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> myActivity.onBackPressed()
        }
        return false
    }

    private fun subscribeToNetworkEvents() {
        NetworkEvents.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Event.ConnectivityAvailable ->
                    if (mAdapter.itemCount == 0)
                        mAdapter.notifyItemChanged(positionCurrent)
            }
        })
    }
}