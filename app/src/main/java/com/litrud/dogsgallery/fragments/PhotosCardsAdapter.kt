package com.litrud.dogsgallery.fragments

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.litrud.dogsgallery.R

// TODO доработать
class PhotosCardsAdapter(var listener : ClickListener) : RecyclerView.Adapter<PhotosCardsAdapter.CardViewHolder>() {
    private var photoList = mutableListOf<Drawable>()

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
        holder.imageView.setImageDrawable(photoList[position])
        holder.imageView.contentDescription = "image"
        holder.cardView.setOnClickListener {
            listener.onClick(photoList[position])
        }
    }

    fun update(items : List<Drawable>) {
        photoList = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = photoList.size

    interface ClickListener {
        fun onClick(item : Drawable)
    }
}