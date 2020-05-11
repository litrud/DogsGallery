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
import com.litrud.dogsgallery.DogBreedsListViewModel
import com.litrud.dogsgallery.R
import com.litrud.dogsgallery.network.DogsApiObject


/**
 * A simple [Fragment] subclass.
 * Use the [DogBreedsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DogBreedsListFragment : Fragment() {
    private lateinit var mView : View
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mCardsAdapter : BreedsCardsAdapter
    private lateinit var mViewModel : DogBreedsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = ViewModelProvider(this).get(DogBreedsListViewModel::class.java)
        mViewModel.getListAllBreeds()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dog_breeds_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        mView = view ?: return
        initViews()

        mViewModel.breedsMapObject.observe(viewLifecycleOwner, Observer { obj: DogsApiObject? ->
            updateList(map = obj!!.message)
        })
    }

    private fun initViews() {
        mRecyclerView = mView.findViewById(R.id.dog_breeds_list)
        mRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mCardsAdapter = BreedsCardsAdapter(object : BreedsCardsAdapter.ClickListener {
            override fun onClick(item: String) = openBreed(item)
        })
        mRecyclerView.adapter = mCardsAdapter
    }

    private fun openBreed(breed : String) {
        // TODO
    }

    companion object {
        //@JvmStatic
        fun newInstance() : DogBreedsListFragment = DogBreedsListFragment()
    }

    private fun updateList(map : Map<String, Array<String>>) {
        mCardsAdapter.update(map)
    }
}
