package com.litrud.dogsgallery.listphoto

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
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.litrud.dogsgallery.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class PhotoListFragment : Fragment() {
    private lateinit var myActivity: AppCompatActivity
    private val viewModel: PhotosViewModel by sharedViewModel()
    private lateinit var args: PhotoListFragmentArgs
    private lateinit var textEmpty: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var mAdapter: PhotoListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        args = PhotoListFragmentArgs.fromBundle(requireArguments())

        // number of columns in list
        val spanCount = 4

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

        // customize the toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar).apply {
            title = args.breedFull
        }
        myActivity = (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_red)
        }

        textEmpty = view.findViewById(R.id.message_empty_pl)
        progressBar = view.findViewById(R.id.progress_bar_pl)

        // list
        recyclerView = view.findViewById<RecyclerView>(R.id.photo_list).apply {
                layoutManager = GridLayoutManager(this@PhotoListFragment.context, spanCount)
                adapter = mAdapter
                addOnScrollListener(preloader)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            // request photo URLs
            getPhotosURLsByBreed(args.breedKeyword, args.breedHyphenated)
            // observe photo URLs
            urlList.observe(viewLifecycleOwner, Observer { urls: List<String> ->
                if (urls.isEmpty())
                    textEmpty.visibility = View.VISIBLE
                else {
                    textEmpty.visibility = View.GONE
                    mAdapter.update(urls)
                }
                progressBar.visibility = View.GONE
            })
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> myActivity.onBackPressed()
        }
        return false
    }
}