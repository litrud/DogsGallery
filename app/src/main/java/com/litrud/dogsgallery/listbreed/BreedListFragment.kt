package com.litrud.dogsgallery.listbreed

import android.content.Intent
import android.content.IntentFilter
import android.net.*
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
import com.litrud.dogsgallery.network.NetworkConnectionBroadcastReceiver
import org.koin.androidx.viewmodel.ext.android.viewModel


class BreedListFragment : Fragment() {
    private val viewModel: BreedListViewModel by viewModel()
    private val mAdapter = BreedListAdapter()
    private lateinit var textMessage: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private val connectionReceiver = NetworkConnectionBroadcastReceiver(qetNetworkCallback())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breed_list, container, false).apply {
            toolbar = findViewById(R.id.toolbar)
            textMessage = findViewById(R.id.message_empty_bl)
            progressBar = findViewById(R.id.progress_bar_bl)
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
                    map: Map<String, MutableList<String>> -> run {
                        if (map.isEmpty()) {
                            showMessage(R.string.msg_empty.toString())
                        } else {
                            mAdapter.update(map)
                            showList()
                        }
                    }
            })
            serverErrorMessage.observe(viewLifecycleOwner, Observer {
                    servErrMsg: String -> showMessage(servErrMsg)
            })
        }
    }

    override fun onResume() { super.onResume()
        requireActivity().registerReceiver(
            connectionReceiver,
            IntentFilter(Intent.ACTION_MANAGE_NETWORK_USAGE)
        )
    }

    override fun onPause() { super.onPause()
        requireActivity().unregisterReceiver(connectionReceiver)
    }

    private fun showList() {
        textMessage.visibility = View.VISIBLE
        textMessage.visibility = View.GONE
        progressBar.visibility = View.GONE
        toolbar.title = getString(R.string.breeds)
    }

    private fun showMessage(message: String) {
        textMessage.text = message
        textMessage.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        toolbar.title = ""
    }


    private fun qetNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) { super.onAvailable(network)
            showList()
        }
        override fun onUnavailable() { super.onUnavailable()
            showMessage("Network unavailable")
        }
    }
}