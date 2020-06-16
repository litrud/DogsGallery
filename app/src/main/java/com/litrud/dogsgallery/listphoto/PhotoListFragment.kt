package com.litrud.dogsgallery.listphoto

import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.google.android.material.snackbar.Snackbar
import com.litrud.dogsgallery.DisableSwipeBehavior
import com.litrud.dogsgallery.R
import com.litrud.dogsgallery.network.monitoring.Event
import com.litrud.dogsgallery.network.monitoring.NetworkEvents
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PhotoListFragment : Fragment() {
    private val viewModel: PhotosViewModel by sharedViewModel()
    private lateinit var myActivity: AppCompatActivity
    private lateinit var args: PhotoListFragmentArgs
    private lateinit var textMessage: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var mAdapter: PhotoListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var snack: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        args = PhotoListFragmentArgs.fromBundle(requireArguments())

        // number of columns depends on screen orientation
        val spanCount = if (
            resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        ) 7 else 4

        // get dimensions according to screen width
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        val width = metrics.widthPixels
        val squareSize = width / spanCount

        // list adapter
        mAdapter = PhotoListAdapter(this, squareSize, squareSize)

        // list preloader
        val sizeProvider = FixedPreloadSizeProvider<String>(squareSize, squareSize)
        val preloader = RecyclerViewPreloader(
            Glide.with(this), mAdapter, sizeProvider, 5
        )

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_photo_list, container, false)
        view?.apply {
            // customize toolbar
            toolbar = findViewById<Toolbar>(R.id.toolbar).apply {
                title = args.breedFull
            }
            textMessage = findViewById(R.id.message_empty_pl)
            progressBar = findViewById(R.id.progress_bar_pl)
            // list
            recyclerView = findViewById<RecyclerView>(R.id.photo_list).apply {
                layoutManager = GridLayoutManager(this@PhotoListFragment.context, spanCount)
                adapter = mAdapter
                addOnScrollListener(preloader)
            }
        }

        // set up Back button
        myActivity = (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_red)
        }

        // snack bar
        snack = Snackbar.make(
            view,
            getString(R.string.msg_internet_unavailable),
            Snackbar.LENGTH_INDEFINITE
        )
        // disable swipe behaviour
        val layout = snack.view
        layout.viewTreeObserver.addOnGlobalLayoutListener {
            val lp = layout.layoutParams
            if (lp is CoordinatorLayout.LayoutParams) {
                lp.behavior = DisableSwipeBehavior()
                layout.layoutParams = lp
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get current breed
        val breed = args.breedKeyword
        val fullBreed = args.breedFull

        viewModel.apply {
            // request sub-breeds list by breed
            getListAllSubBreeds(breed)

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
                    mAdapter.update(urls)
                    showList()
                }
            })

            serverErrorMessage.observe(viewLifecycleOwner, Observer { errorMessage: Int ->
                showMessage(getString(errorMessage))
                toolbar.title = args.breedFull
            })
        }

        // respond to network status changes
        NetworkEvents.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Event.ConnectivityAvailable -> {
                    if (mAdapter.itemCount == 0) viewModel.getListAllSubBreeds(args.breedKeyword)
                    if (snack.isShown) snack.dismiss()
                }
                is Event.ConnectivityLost -> {
                    snack.show()
                }
            }
        })
    }

    private fun showList() {
        recyclerView.visibility = View.VISIBLE
        textMessage.visibility = View.GONE
        progressBar.visibility = View.GONE
        toolbar.title = args.breedFull
        if (snack.isShown) snack.dismiss()
    }

    private fun showMessage(message: String) {
        textMessage.text = message
        textMessage.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        toolbar.title = ""
        if (snack.isShown) snack.dismiss()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> myActivity.onBackPressed()
        }
        return false
    }
}