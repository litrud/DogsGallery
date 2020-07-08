package com.litrud.dogsgallery.listbreed

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.litrud.dogsgallery.R
import com.litrud.dogsgallery.network.monitoring.Event
import com.litrud.dogsgallery.network.monitoring.NetworkEvents
import kotlinx.android.synthetic.main.fragment_breed_list.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class BreedListFragment : Fragment() {
    private val viewModel: BreedListViewModel by viewModel()
    private val mAdapter = BreedListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breed_list, container, false)
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        appbar_breeds.setExpanded(
            resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        )

        with(breed_list) {
            layoutManager = GridLayoutManager(this@BreedListFragment.context, 1)
            adapter = mAdapter
        }

        // request data
        viewModel.getListAllBreed()
        subscribeToData()
        subscribeToNetworkEvents()
    }

    private fun subscribeToData() {
        with(viewModel) {
            breedMap.observe(viewLifecycleOwner, Observer { map: Map<String, MutableList<String>> ->
                run {
                    if (map.isEmpty()) {
                        showMessage(getString(R.string.msg_empty))
                    } else {
                        mAdapter.update(map)
                        showList()
                    }
                }
            })
            serverErrorMessage.observe(viewLifecycleOwner, Observer { errorMessage: Int ->
                showMessage(getString(errorMessage))
            })
        }
    }

    private fun subscribeToNetworkEvents() {
        NetworkEvents.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Event.ConnectivityAvailable ->
                    if (mAdapter.itemCount == 0) viewModel.getListAllBreed()
            }
        })
    }

    private fun showList() {
        breed_list.visibility = View.VISIBLE
        message_empty_bl.visibility = View.GONE
        progress_bar_bl.visibility = View.GONE
        toolbar.title = getString(R.string.breeds)
    }

    private fun showMessage(message: String) {
        message_empty_bl.text = message
        message_empty_bl.visibility = View.VISIBLE
        breed_list.visibility = View.GONE
        progress_bar_bl.visibility = View.GONE
        toolbar.title = ""
    }
}