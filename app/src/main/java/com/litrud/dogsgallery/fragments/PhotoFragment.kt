package com.litrud.dogsgallery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.litrud.dogsgallery.R

// the fragment initialization parameters
private const val ARG_URL = "url"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotoFragment : Fragment() {
    private lateinit var mImageView: ImageView
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onStart() {
        super.onStart()
        view?.let {
            mImageView = requireView().findViewById(R.id.photo)

            Glide.with(this)
                .load(url)
                .into(mImageView)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param url Link to the photo.
         * @return A new instance of fragment PhotoFragment.
         */
        @JvmStatic
        fun getInstance(url: String) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                }
            }
    }
}
