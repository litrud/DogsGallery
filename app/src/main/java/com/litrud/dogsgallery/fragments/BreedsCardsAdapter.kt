package com.litrud.dogsgallery.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.litrud.dogsgallery.R

class BreedsCardsAdapter(var listener : ClickListener) : RecyclerView.Adapter<BreedsCardsAdapter.CardViewHolder>() {
    private var breedList = mutableListOf<String>()
    private var breedListWithDash = mutableListOf<String>()
        get() = field

    inner class CardViewHolder(itemView: CardView) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView
        val textView : TextView = cardView.findViewById(R.id.text_breed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_breed, parent, false)
        return CardViewHolder(cardView as CardView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.textView.text = breedList[position]
        holder.cardView.setOnClickListener {
            listener.onClick(breedList[position])
        }
    }

    fun update(map : Map<String, Array<String>>) {
        breedList.clear()
        breedListWithDash.clear()
        for((key, value) in map) {
            if (value.isNotEmpty()) {
                for (v in value) {
                    breedList.add("$key $v")
                    breedListWithDash.add("$key-$v")
                }
            } else {
                breedList.add(key)
                breedListWithDash.add(key)
            }
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = breedList.size

    interface ClickListener {
        fun onClick(item : String)
    }
}