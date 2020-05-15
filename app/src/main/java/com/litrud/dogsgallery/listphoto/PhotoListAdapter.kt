package com.litrud.dogsgallery.listphoto

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.litrud.dogsgallery.R


class PhotoListAdapter(private var fragment: Fragment)
    : RecyclerView.Adapter<PhotoListAdapter.CardViewHolder>() {

    private var urlList = mutableListOf<String>()

    inner class CardViewHolder(itemView: CardView) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView
        val imageView : ImageView = cardView.findViewById(R.id.image_view)
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
            .into(holder.imageView)

        holder.cardView.setOnClickListener {
            // pass argument within the action
            val action = PhotoListFragmentDirections.actionPhotoListFragmentToPhotoFragment(url)
            it.findNavController().navigate(action)
        }
    }

    fun update(links: MutableList<String>, breedHyphenated: String) {
        // first select from links only those links that contain breedHyphenated
        val urls = mutableListOf<String>()
        links.forEach {
            if (it.contains(breedHyphenated))
                urls.add(it)
        }
        urlList = urls
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = urlList.size
}