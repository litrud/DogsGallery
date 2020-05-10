package com.litrud.dogsgallery

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * A simple [Fragment] subclass.
 * Use the [DogsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DogsListFragment : Fragment() {
    private lateinit var mView : View
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mCardsAdapter : CardsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dogs_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        mView = view ?: return
        initViews()
    }

    private fun initViews() {
        mRecyclerView = mView.findViewById(R.id.dogs_list)
        mRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mRecyclerView.adapter = CardsAdapter( object : CardsAdapter.ClickListener {
            override fun onClick(item: Drawable) = openImage(item)
        })
//        mCardsAdapter = CardsAdapter( CardsAdapter.ClickListener() {d : Drawable -> openImage(d)})
    }

    private fun openImage(drawable : Drawable) {
        TODO()
    }

    companion object {
        @JvmStatic
        fun newInstance() = DogsListFragment()
    }

    private fun updateList(items : List<Drawable>) {
        mCardsAdapter.update(items)
    }
}
