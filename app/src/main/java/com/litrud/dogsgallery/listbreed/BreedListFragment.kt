package com.litrud.dogsgallery.listbreed

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
import com.litrud.dogsgallery.network.apiobject.ApiObjectMapString


class BreedListFragment : Fragment() {
    private val mAdapter = BreedListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_breed_list, container, false)

        view.findViewById<RecyclerView>(R.id.breeds_list).apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = mAdapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewModelProvider(this).get(BreedListViewModel::class.java).apply {
            // request breed list
            getListAllBreed()
            // observe breed list
            breedsApiObjectMapString.observe(viewLifecycleOwner, Observer { obj: ApiObjectMapString? ->
                mAdapter.update(map = obj!!.message)
            })
        }
    }
}
