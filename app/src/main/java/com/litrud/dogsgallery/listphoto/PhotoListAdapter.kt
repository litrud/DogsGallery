package com.litrud.dogsgallery.listphoto

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.DeadObjectException
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.litrud.dogsgallery.R
import java.util.*


class PhotoListAdapter(
    private val context: Context
  , private val squareSize: Int
) : RecyclerView.Adapter<PhotoListAdapter.ViewHolder>()
    , ListPreloader.PreloadModelProvider<String>
{
//    init {
//        setHasStableIds(true)
//    }
    private var urlList = Collections.emptyList<String>()

    inner class ViewHolder(itemView: CardView) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView
        val imageView: ImageView = cardView.findViewById<ImageView>(R.id.image_view).apply {
            layoutParams.height = squareSize
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListAdapter.ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_photo, parent, false)
        return ViewHolder(cardView as CardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = urlList[position]
        glideRequestBuilder(url)
            .into(holder.imageView)

        holder.cardView.setOnClickListener {
            val action = PhotoListFragmentDirections.actionPhotoListFragmentToPhotoFragment(
                position
            )
            it.findNavController().navigate(action)
        }
    }

    private fun glideRequestBuilder(url: String?) : RequestBuilder<Drawable> {
        return Glide.with(context)
            .load(url)
            .placeholder(R.mipmap.ic_paw)
            .thumbnail(0.2f)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
    }

    fun update(links: List<String>) {
        urlList = links
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int
            = urlList.size

    override fun getPreloadItems(position: Int): List<String>
            = urlList.subList(position, position + 1)

    override fun getPreloadRequestBuilder(url: String): RequestBuilder<Drawable>?
            = glideRequestBuilder(url)
}