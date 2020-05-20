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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        args = PhotoListFragmentArgs.fromBundle(requireArguments())

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_photo_list, container, false)

        // number of columns in list
        val spanCount = 4

        // get dimensions according to screen width
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(metrics)
        val width = metrics.widthPixels
        val imageWidthPixels = width / spanCount
        val imageHeightPixels = imageWidthPixels

        // list adapter
        mAdapter = PhotoListAdapter(this, imageWidthPixels, imageHeightPixels)

        // list preloader
        val sizeProvider = FixedPreloadSizeProvider<String>(imageWidthPixels, imageHeightPixels)
        val preloader = RecyclerViewPreloader(
            Glide.with(this), mAdapter, sizeProvider, 5
        )

        textEmpty = view.findViewById(R.id.message_empty_pl)
        progressBar = view.findViewById<ProgressBar>(R.id.progress_bar_pl).apply {
            visibility = View.VISIBLE
        }

        // list
        view.findViewById<RecyclerView>(R.id.photo_list).apply {
            layoutManager = GridLayoutManager(context, spanCount)
            adapter = mAdapter
            addOnScrollListener(preloader)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewModelProvider(this).get(PhotoListViewModel::class.java).apply {
            // request photo URLs
            getPhotosURLsByBreed(args.breedKeyword)
            // observe photo URLs
            urlList.observe(viewLifecycleOwner, Observer { urls: MutableList<String> ->
                if (urls.isEmpty())
                    textEmpty.visibility = View.VISIBLE
                else {
                    textEmpty.visibility = View.GONE
                    mAdapter.update(urls, args.breedHyphenated)
                }
                progressBar.visibility = View.GONE
            })
        }
    }
}