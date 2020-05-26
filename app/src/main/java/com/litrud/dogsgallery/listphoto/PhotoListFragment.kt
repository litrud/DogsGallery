package com.litrud.dogsgallery.listphoto

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.litrud.dogsgallery.R


class PhotoListFragment : Fragment() {
    private lateinit var args: PhotoListFragmentArgs
    private lateinit var textEmpty: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var mAdapter: PhotoListAdapter
    private lateinit var viewModel: PhotosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        return inflater.inflate(R.layout.fragment_photo_list, container, false).apply {
            textEmpty = findViewById(R.id.message_empty_pl)
            progressBar = findViewById<ProgressBar>(R.id.progress_bar_pl).apply {
                visibility = View.VISIBLE
            }
            // list
            findViewById<RecyclerView>(R.id.photo_list).apply {
                layoutManager = GridLayoutManager(this@PhotoListFragment.context, spanCount)
                adapter = mAdapter
                addOnScrollListener(preloader)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PhotosViewModel::class.java)
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
}