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
import com.litrud.dogsgallery.vm.BreedsListViewModel
import com.litrud.dogsgallery.R
import com.litrud.dogsgallery.fragments.adapters.BreedCardsAdapter
import com.litrud.dogsgallery.network.ApiObjectMapString


/**
 * A simple [Fragment] subclass.
 * Use the [BreedListFragment.getInstance] factory method to
 * create an instance of this fragment.
 */
class BreedListFragment(private var openListener: OpenListener) : Fragment() {
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mCardsAdapter: BreedCardsAdapter
    private lateinit var mViewModel: BreedsListViewModel

    interface OpenListener {
        fun openPhotosList(breed_hyphenated: String, breed_keyword: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(this).get(BreedsListViewModel::class.java)
        mViewModel.getListAllBreeds()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breed_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        view?.let {
            initViews()
        }
        mViewModel.breedsApiObjectMapString.observe(viewLifecycleOwner, Observer { obj: ApiObjectMapString? ->
            updateList(map = obj!!.message)
        })
    }

    private fun initViews() {
        mRecyclerView = requireView().findViewById(R.id.breeds_list)
        mRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mCardsAdapter =
            BreedCardsAdapter(object :
                BreedCardsAdapter.ClickListener {
                override fun onClick(breed_hyphenated: String, breed_keyword: String)
                        = openBreedList(breed_hyphenated, breed_keyword)
            })
        mRecyclerView.adapter = mCardsAdapter
    }

    private fun updateList(map : Map<String, MutableList<String>>) {
        mCardsAdapter.update(map)
    }

    private fun openBreedList(breed_hyphenated: String, breed_keyword: String) {
        openListener.openPhotosList(breed_hyphenated, breed_keyword)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param listener Photos list opener.
         * @return A new instance of fragment BreedsListFragment.
         */
        @JvmStatic
        fun getInstance(listener: OpenListener): BreedListFragment = BreedListFragment(listener)
    }
}
