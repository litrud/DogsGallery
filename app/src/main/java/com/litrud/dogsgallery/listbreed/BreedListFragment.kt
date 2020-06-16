package com.litrud.dogsgallery.listbreed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.litrud.dogsgallery.DisableSwipeBehavior
import com.litrud.dogsgallery.R
import com.litrud.dogsgallery.network.monitoring.Event
import com.litrud.dogsgallery.network.monitoring.NetworkEvents
import org.koin.androidx.viewmodel.ext.android.viewModel


class BreedListFragment : Fragment() {
    private val viewModel: BreedListViewModel by viewModel()
    private val mAdapter = BreedListAdapter()
    private lateinit var textMessage: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var snack: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_breed_list, container, false)
        view?.apply {
            toolbar = findViewById(R.id.toolbar)
            textMessage = findViewById(R.id.message_empty_bl)
            progressBar = findViewById(R.id.progress_bar_bl)
            recyclerView = findViewById<RecyclerView>(R.id.breed_list).apply {
                layoutManager = GridLayoutManager(this@BreedListFragment.context, 1)
                adapter = mAdapter
            }
            snack = Snackbar.make(
                view,
                getString(R.string.msg_internet_unavailable),
                Snackbar.LENGTH_INDEFINITE
            )
            // disable swipe behaviour
            val layout = snack.view
            layout.viewTreeObserver.addOnGlobalLayoutListener {
                val lp = layout.layoutParams
                if (lp is CoordinatorLayout.LayoutParams) {
                    lp.behavior = DisableSwipeBehavior()
                    layout.layoutParams = lp
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            // request data
            getListAllBreed()
            breedMap.observe(viewLifecycleOwner, Observer {
                    map: Map<String, MutableList<String>> -> run {
                        if (map.isEmpty()) {
                            showMessage(getString(R.string.msg_empty))
                        } else {
                            mAdapter.update(map)
                            showList()
                        }
                    }
            })
            serverErrorMessage.observe(viewLifecycleOwner, Observer {
                    errorMessage: Int -> showMessage(getString(errorMessage))
            })
        }

        // respond to network status changes
        NetworkEvents.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Event.ConnectivityAvailable -> {
                    if (mAdapter.itemCount == 0) viewModel.getListAllBreed()
                    if (snack.isShown) snack.dismiss()
                }
                is Event.ConnectivityLost -> {
                    snack.show()
                }
            }
        })
    }

    private fun showList() {
        recyclerView.visibility = View.VISIBLE
        textMessage.visibility = View.GONE
        progressBar.visibility = View.GONE
        toolbar.title = getString(R.string.breeds)
        if (snack.isShown) snack.dismiss()
    }

    private fun showMessage(message: String) {
        textMessage.text = message
        textMessage.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        toolbar.title = ""
        if (snack.isShown) snack.dismiss()
    }
}