package com.litrud.dogsgallery.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.litrud.dogsgallery.R


class PhotoCardsAdapter(private var fragment: Fragment,
                        private var clickListener : ClickListener)
    : RecyclerView.Adapter<PhotoCardsAdapter.CardViewHolder>() {

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
        val url = urlList.get(position)
        Glide.with(fragment)
            .load(url)
            .into(holder.imageView)

        holder.cardView.setOnClickListener {
            clickListener.onClick(position)
        }
    }

    fun update(linksList: MutableList<String>) {
        urlList = linksList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = urlList.size

    interface ClickListener{
        fun onClick(position : Int)
    }
}