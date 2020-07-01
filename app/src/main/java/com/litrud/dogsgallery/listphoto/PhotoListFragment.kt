package com.litrud.dogsgallery.listphoto

import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.litrud.dogsgallery.R
import com.litrud.dogsgallery.network.monitoring.Event
import com.litrud.dogsgallery.network.monitoring.NetworkEvents
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PhotoListFragment : Fragment() {
    private val viewModel: PhotosViewModel by sharedViewModel()
    private lateinit var containingActivity: AppCompatActivity
    private lateinit var args: PhotoListFragmentArgs
    private lateinit var textMessage: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var mAdapter: PhotoListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var breed: String
    private lateinit var fullBreed: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // get current breed
        args = PhotoListFragmentArgs.fromBundle(requireArguments())
        breed = args.breedKeyword
        fullBreed = args.breedFull
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val columnsNumber = determineColumnsNumber()
        val squareSize = determineItemWidth(columnsNumber)

        // list adapter
        mAdapter = PhotoListAdapter(squareSize)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_photo_list, container, false)

        with(view) {
            // customize toolbar
            toolbar = findViewById<Toolbar>(R.id.toolbar).apply {
                title = args.breedFull
            }
            textMessage = findViewById(R.id.message_empty_pl)
            progressBar = findViewById(R.id.progress_bar_pl)
            // list
            recyclerView = findViewById<RecyclerView>(R.id.photo_list).apply {
                layoutManager = GridLayoutManager(this@PhotoListFragment.context, columnsNumber)
                adapter = mAdapter
            }
        }

        // set up Back button
        containingActivity = (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_red)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingIndicator()      // TODO *** in vain...

        // request sub-breeds list by breed
        viewModel.getListAllSubBreeds(breed)

        subscribeToData()
        subscribeToNetworkEvents()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home ->
                containingActivity.onBackPressed()
        }
        return false
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
                    if (fullBreed.contains(it))
                        sub = it
                }
                // request photos URLs
                if (sub == "")
                    getPhotosURLsByBreed(breed)
                else
                    getPhotosURLsBySubBreed(breed, sub)
            })
            urlList.observe(viewLifecycleOwner, Observer { urls: List<String> ->
                if (urls.isEmpty())
                    showMessage(getString(R.string.msg_empty))
                else {
                    mAdapter.update(urls)       // TODO ***
                    showList()
                }
            })
            serverErrorMessage.observe(viewLifecycleOwner, Observer { errorMessage: Int ->
                showMessage(getString(errorMessage))
                toolbar.title = fullBreed
            })
        }
    }

    private fun subscribeToNetworkEvents() {
        NetworkEvents.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Event.ConnectivityAvailable ->
                    if (mAdapter.itemCount == 0) viewModel.getListAllSubBreeds(breed)
            }
        })
    }

    private fun showLoadingIndicator() {
        recyclerView.visibility = View.GONE
        textMessage.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun showList() {
        recyclerView.visibility = View.VISIBLE
        textMessage.visibility = View.GONE
        progressBar.visibility = View.GONE
        toolbar.title = fullBreed
    }

    private fun showMessage(message: String) {
        textMessage.text = message
        textMessage.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        toolbar.title = ""
    }
}