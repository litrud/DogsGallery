package com.litrud.dogsgallery.listphoto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.litrud.dogsgallery.R


private const val ARG_URL = "url"

class PhotoPage : Fragment() {
    private lateinit var url: String
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            url = it.getString(ARG_URL)!!
        }

        return inflater.inflate(R.layout.page_photo, container, false).apply {
            imageView = findViewById(R.id.photo)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_placeholder)
            .into(imageView)
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