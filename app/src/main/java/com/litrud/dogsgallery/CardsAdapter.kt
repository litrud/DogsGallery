package com.litrud.dogsgallery

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class CardsAdapter(var listener : ClickListener) : RecyclerView.Adapter<CardsAdapter.CardViewHolder>() {
    private var itemList = mutableListOf<Drawable>()

    inner class CardViewHolder(itemView: CardView) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView
        val imageView : ImageView = cardView.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card, parent, false)
        return CardViewHolder(cardView as CardView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.imageView.setImageDrawable(itemList[position])
        holder.imageView.contentDescription = "image"
        holder.cardView.setOnClickListener {
            listener.onClick(itemList[position])
        }
    }

    fun update(items : List<Drawable>) {
        itemList = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = itemList.size

    interface ClickListener {
        fun onClick(item : Drawable)
    }
}