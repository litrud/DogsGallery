package com.litrud.dogsgallery.fragments

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
import com.litrud.dogsgallery.fragments.adapters.PhotoCardsAdapter
import com.litrud.dogsgallery.vm.PhotosListViewModel

// the fragment initialization parameters
private const val ARG_BREED_HYPERNATED = "breed_hyphenated"
private const val ARG_BREED_KEYWORD = "breed_keyword"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoListFragment.getInstance] factory method to
 * create an instance of this fragment.
 */
class PhotoListFragment(private var openListener: OpenListener) : Fragment() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mCardsAdapter: PhotoCardsAdapter
    private lateinit var mViewModel: PhotosListViewModel
    private var urls: MutableList<String> = mutableListOf()
    private var breed_hyphenated: String? = null
    private var breed_keyword: String? = null

    interface OpenListener {
        fun openPhoto(url: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            breed_hyphenated = it.getString(ARG_BREED_HYPERNATED)
            breed_keyword = it.getString(ARG_BREED_KEYWORD)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(this).get(PhotosListViewModel::class.java)
        mViewModel.getPhotosURLsByBreed(breed_keyword!!)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        view?.let {
            initViews()

            mViewModel.urlList.observe(viewLifecycleOwner, Observer { links: MutableList<String> ->
                /*
                    first select from links only those links that contain breed_hyphenated
                 */
                var i = 0
                links.forEach {
                    if (it.contains(breed_hyphenated!!))
                        urls.add(it)
                }

                updateList(urls)
            })
        }
    }

    private fun initViews() {
        mRecyclerView = requireView().findViewById(R.id.photos_by_breed)
        mRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mCardsAdapter = PhotoCardsAdapter(this, object :
                PhotoCardsAdapter.ClickListener {
                override fun onClick(position: Int) = openPhoto(position)
            })
        mRecyclerView.adapter = mCardsAdapter
    }

    private fun updateList(urlList: MutableList<String>) {
        mCardsAdapter.update(urlList)
    }

    private fun openPhoto(position: Int) {
        val url = urls[position]
        openListener.openPhoto(url)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param breed_hyphenated The detailed name of the dog breed with a dash.
         * @param breed_keyword The simple name of the dog breed.
         * @param listener Photo opener.
         * @return A new instance of fragment PhotosListFragment.
         */
        @JvmStatic
        fun getInstance(breed_hyphenated: String, breed_keyword: String, listener: OpenListener) =
            PhotoListFragment(listener).apply {
                arguments = Bundle().apply {
                    putString(ARG_BREED_HYPERNATED, breed_hyphenated)
                    putString(ARG_BREED_KEYWORD, breed_keyword)
                }
            }
    }
}
