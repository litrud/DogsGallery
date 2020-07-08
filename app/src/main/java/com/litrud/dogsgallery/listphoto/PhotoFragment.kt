package com.litrud.dogsgallery.listphoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.litrud.dogsgallery.R
import com.litrud.dogsgallery.di.sharedGraphViewModel
import com.litrud.dogsgallery.network.monitoring.Event
import com.litrud.dogsgallery.network.monitoring.NetworkEvents
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.android.synthetic.main.toolbar.*


class PhotoFragment : Fragment() {
    private lateinit var containingActivity: AppCompatActivity
    private val viewModel: PhotosViewModel by sharedGraphViewModel(R.id.photo_gallery)
    private lateinit var mAdapter: FragmentStateAdapter
    private lateinit var args: PhotoFragmentArgs
    private var positionCurrent: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        args = PhotoFragmentArgs.fromBundle(requireArguments())
        positionCurrent = args.position
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_photo, container, false)
        // set up Back button
        containingActivity = (activity as AppCompatActivity).apply {
//            setSupportActionBar(toolbar)   // kotlin android extension is not working here
            setSupportActionBar(view.findViewById(R.id.toolbar))
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_red)
        }
        return view
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        toolbar.title = args.fullBreed
        mAdapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = viewModel.getItemCount()
            override fun createFragment(position: Int): Fragment {
                positionCurrent = position
                return PhotoPage.newInstance(viewModel.getUrl(position))
            }
        }
        // View Pager 2
        with(viewPager2_photos) {
            adapter = mAdapter
            setCurrentItem(args.position, false)
        }

        subscribeToNetworkEvents()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> containingActivity.onBackPressed()
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