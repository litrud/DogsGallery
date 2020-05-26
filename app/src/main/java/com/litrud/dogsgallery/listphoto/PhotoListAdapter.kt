package com.litrud.dogsgallery.listphoto

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.litrud.dogsgallery.R
import java.util.*


class PhotoListAdapter(private val fragment: Fragment,
                       private val imageWidthPixels: Int,
                       private val imageHeightPixels: Int)
    : RecyclerView.Adapter<PhotoListAdapter.CardViewHolder>()
    , ListPreloader.PreloadModelProvider<String> {

    private var urlList = Collections.emptyList<String>()

    inner class CardViewHolder(itemView: CardView) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView
        val imageView: ImageView = cardView.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_photo, parent, false)
        return CardViewHolder(cardView as CardView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val url = urlList[position]

        Glide.with(fragment)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .override(imageWidthPixels, imageHeightPixels)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(holder.imageView)

        holder.cardView.setOnClickListener {
            val action = PhotoListFragmentDirections.actionPhotoListFragmentToPhotoFragment(
                position
            )
            it.findNavController().navigate(action)
        }
    }

    fun update(links: List<String>) {
        urlList = links
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int
            = urlList.size

    override fun getPreloadItems(position: Int): List<String> {
        return urlList.subList(position, position + 1)
    }

    override fun getPreloadRequestBuilder(url: String): RequestBuilder<*>? {
        return Glide.with(fragment)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .override(imageWidthPixels, imageHeightPixels)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    }
}