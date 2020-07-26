package com.litrud.dogsgallery.listphoto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.litrud.dogsgallery.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_photo.*
import java.util.*


class PhotoListAdapter(
    private val squareSize: Int,
    private val fullBreed: String
) : RecyclerView.Adapter<PhotoListAdapter.ViewHolder>() {

    private var urlList = Collections.emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(position)
    }

    override fun getItemCount(): Int
            = urlList.size

    override fun onViewRecycled(holder: ViewHolder) { super.onViewRecycled(holder)
        holder.cleanup()
    }

    inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val imageView = image_view.apply {
            layoutParams.height = squareSize
        }

        fun bindItem(position: Int) {
            picassoRequestCreator(urlList[position])
                .into(imageView)

            containerView.setOnClickListener {
                val action = PhotoListFragmentDirections.actionPhotoListFragmentToPhotoFragment(
                    position,
                    fullBreed
                )
                it.findNavController().navigate(action)
            }
        }

        fun cleanup() {
            Picasso.get()
                .cancelRequest(imageView)
        }
    }

    fun update(links: List<String>) {
        urlList = links
        notifyDataSetChanged()
    }

    companion object {
        fun picassoRequestCreator(url: String?) : RequestCreator {
            return Picasso.get()
                .load(url)
                .resize(100, 100)
                .placeholder(R.mipmap.ic_paw)
                .error(R.mipmap.error_img)
                .centerCrop()
        }
    }
}