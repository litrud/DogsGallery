package com.litrud.dogsgallery.listphoto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.litrud.dogsgallery.R


class PhotoListFragment : Fragment() {
    private lateinit var args: PhotoListFragmentArgs
    private val mAdapter = PhotoListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        args = PhotoListFragmentArgs.fromBundle(requireArguments())

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_photo_list, container, false)

        view.findViewById<RecyclerView>(R.id.photos_by_breed).apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = mAdapter
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
                mAdapter.update(urls, args.breedHyphenated)
            })
        }
    }
}