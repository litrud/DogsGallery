package com.litrud.dogsgallery.listbreed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.litrud.dogsgallery.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class BreedListFragment : Fragment() {
    private val viewModel: BreedListViewModel by viewModel()
    private val mAdapter = BreedListAdapter()
    private lateinit var textMessage: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breed_list, container, false).apply {
            toolbar = findViewById<Toolbar>(R.id.toolbar).apply {
                title = getString(R.string.breeds)
            }
            textMessage = findViewById(R.id.message_empty_bl)
            progressBar = findViewById<ProgressBar>(R.id.progress_bar_bl)
            recyclerView = findViewById<RecyclerView>(R.id.breed_list).apply {
                layoutManager = GridLayoutManager(this@BreedListFragment.context, 1)
                adapter = mAdapter
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            // request data
            getListAllBreed()

            breedMap.observe(viewLifecycleOwner, Observer {
                    map: Map<String, MutableList<String>> ->
                        if (map.isEmpty()) {
                            textMessage.text = R.string.msg_empty.toString()
                            textMessage.visibility = View.VISIBLE
                        } else {
                            textMessage.visibility = View.GONE
                            mAdapter.update(map)
                        }
                        progressBar.visibility = View.GONE
            })

            serverErrorMessage.observe(viewLifecycleOwner, Observer {
                    errMsg: String ->
                        textMessage.text = errMsg
                        textMessage.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        toolbar.title = ""
            })
        }
    }
}