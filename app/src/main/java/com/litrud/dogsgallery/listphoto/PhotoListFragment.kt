package com.litrud.dogsgallery.listphoto

import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.litrud.dogsgallery.R
import com.litrud.dogsgallery.di.sharedGraphViewModel
import com.litrud.dogsgallery.network.monitoring.Event
import com.litrud.dogsgallery.network.monitoring.NetworkEvents
import kotlinx.android.synthetic.main.fragment_photo_list.*
import kotlinx.android.synthetic.main.toolbar.*


class PhotoListFragment : Fragment() {
    private val viewModel: PhotosViewModel by sharedGraphViewModel(R.id.photo_gallery)
    private lateinit var containingActivity: AppCompatActivity
    private lateinit var args: PhotoListFragmentArgs
    private lateinit var mRecycler: RecyclerView
    private lateinit var mAdapter: PhotoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        args = PhotoListFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_photo_list, container, false)
        // set up Back button
        containingActivity = (requireActivity() as AppCompatActivity).apply {
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
        appbar_photos.setExpanded(
            resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        )

        val columnsNumber = determineColumnsNumber()
        val squareSize = determineItemWidth(columnsNumber)
        toolbar.title = args.breedFull
        mAdapter = PhotoListAdapter(squareSize, args.breedFull)
        mRecycler = photo_list.apply {
            layoutManager = GridLayoutManager(this@PhotoListFragment.context, columnsNumber)
            adapter = mAdapter
        }
        // request sub-breeds list by breed
        viewModel.getListAllSubBreeds(args.breedKeyword)
        subscribeToData()
        subscribeToNetworkEvents()
    }

    override fun onOptionsItemSelected(
        menuItem: MenuItem
    ): Boolean {
        when (menuItem.itemId) {
            android.R.id.home ->
                containingActivity.onBackPressed()
        }
        return false
    }

    override fun onDestroyView() { super.onDestroyView()
        mRecycler.adapter = null
    }

    // number of columns depends on screen orientation
    private fun determineColumnsNumber(): Int {
        return if (
            resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        ) 7 else 4
    }

    // get item dimension according to screen width
    private fun determineItemWidth(columnsNumber: Int): Int {
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        val screenWidth = metrics.widthPixels
        return screenWidth / columnsNumber
    }

    private fun subscribeToData() {
        with(viewModel) {
            subBreeds.observe(viewLifecycleOwner, Observer { subBreeds: List<String> ->
                // find sub-breed
                var sub = ""
                subBreeds.forEach {
                    if (args.breedFull.contains(it))
                        sub = it
                }
                // request photos URLs
                if (sub == "")
                    getPhotosURLsByBreed(args.breedKeyword)
                else
                    getPhotosURLsBySubBreed(args.breedKeyword, sub)
            })
            urlList.observe(viewLifecycleOwner, Observer { urls: List<String> ->
                if (urls.isEmpty())
                    showMessage(getString(R.string.msg_empty))
                else {
                    mAdapter.update(urls)
                    showList()
                }
            })
            serverErrorMessage.observe(viewLifecycleOwner, Observer { errorMessage: Int ->
                showMessage(getString(errorMessage))
                toolbar.title = args.breedFull
            })
        }
    }

    private fun subscribeToNetworkEvents() {
        NetworkEvents.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Event.ConnectivityAvailable ->
                    if (mAdapter.itemCount == 0) viewModel.getListAllSubBreeds(args.breedKeyword)
            }
        })
    }

    private fun showList() {
        photo_list.visibility = View.VISIBLE
        message_empty_pl.visibility = View.GONE
        progress_bar_pl.visibility = View.GONE
        toolbar.title = args.breedFull
    }

    private fun showMessage(message: String) {
        message_empty_pl.text = message
        message_empty_pl.visibility = View.VISIBLE
        photo_list.visibility = View.GONE
        progress_bar_pl.visibility = View.GONE
        toolbar.title = ""
    }
}