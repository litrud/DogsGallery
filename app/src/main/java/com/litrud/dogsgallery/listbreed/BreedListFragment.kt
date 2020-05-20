package com.litrud.dogsgallery.listbreed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.litrud.dogsgallery.R
import com.litrud.dogsgallery.network.apiobject.ApiObjectMapString
import org.koin.androidx.viewmodel.ext.android.viewModel


class BreedListFragment : Fragment() {
    private val viewModel: BreedListViewModel by viewModel()

    private val mAdapter = BreedListAdapter()
    private lateinit var textEmpty: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_breed_list, container, false)

        textEmpty = view.findViewById(R.id.message_empty_bl)
        progressBar = view.findViewById<ProgressBar>(R.id.progress_bar_bl).apply {
            visibility = View.VISIBLE
        }

        view.findViewById<RecyclerView>(R.id.breed_list).apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = mAdapter
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListAllBreed()
        viewModel.breedsApiObjectMapString.observe(
            viewLifecycleOwner,
            Observer { obj: ApiObjectMapString? ->
                obj?.let {
                    if (obj.message.isEmpty())
                        textEmpty.visibility = View.VISIBLE
                    else {
                        textEmpty.visibility = View.GONE
                        mAdapter.update(map = obj.message)
                    }
                }
                progressBar.visibility = View.GONE
            })
    }
}