package com.litrud.dogsgallery.listphoto

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.litrud.dogsgallery.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import java.util.*


class PhotoListAdapter(
    private val squareSize: Int
) : RecyclerView.Adapter<PhotoListAdapter.ViewHolder>()
{
//    init {
//        setHasStableIds(true)
//    }
    private var urlList = Collections.emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListAdapter.ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_photo, parent, false)
        return ViewHolder(cardView as CardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(position)
    }

    override fun getItemCount(): Int
            = urlList.size

    inner class ViewHolder(val card: CardView) : RecyclerView.ViewHolder(card) {
        fun bindItem(position: Int) {
            val imageView: ImageView = card.findViewById<ImageView>(R.id.image_view).apply {
                layoutParams.height = squareSize
            }
            card.setOnClickListener {
                val action = PhotoListFragmentDirections.actionPhotoListFragmentToPhotoFragment(
                    position
                )
                it.findNavController().navigate(action)
            }
            picassoRequestCreator(urlList[position])    // TODO ***
                .into(imageView)
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
                .resize(43, 43)
                .placeholder(R.mipmap.ic_paw)
                .error(R.mipmap.error_img)
//                .memoryPolicy(MemoryPolicy.NO_CACHE)
//                .networkPolicy(NetworkPolicy.NO_STORE)
        }
    }
}
