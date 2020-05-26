package com.litrud.dogsgallery.listphoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.litrud.dogsgallery.R


class PhotoFragment : Fragment() {
    private lateinit var viewModel: PhotosViewModel
    private lateinit var viewPager2: ViewPager2
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            position = PhotoFragmentArgs.fromBundle(it).position
        }

        viewModel = ViewModelProvider(requireActivity()).get(PhotosViewModel::class.java)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_photo, container, false)

        viewPager2 = view.findViewById(R.id.viewPager2_photos)
        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = viewModel.getItemCount()
            override fun createFragment(position: Int)
                    = PhotoPage.newInstance(viewModel.getUrl(position))
        }
        viewPager2.setCurrentItem(position, false)

        return view
    }
}