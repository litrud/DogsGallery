package com.litrud.dogsgallery.listphoto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.litrud.dogsgallery.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.page_photo.*


private const val ARG_URL = "url"

class PhotoPage : Fragment() {
    private lateinit var url: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            url = it.getString(ARG_URL)!!
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.page_photo, container, false)
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_paw_vector)
            .error(R.mipmap.error_img)
            .into(photo)
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) = PhotoPage().apply {
            arguments = Bundle().apply {
                putString(ARG_URL, url)
            }
        }
    }
}