package com.litrud.dogsgallery.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.litrud.dogsgallery.R

class BreedCardsAdapter(private var clickListener : ClickListener)
    : RecyclerView.Adapter<BreedCardsAdapter.CardViewHolder>() {

    private var breedList = mutableListOf<String>()
    private var breedListHyphenated = mutableListOf<String>()
    private var breedListKeyword = mutableListOf<String>()

    inner class CardViewHolder(itemView: CardView) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView
        val textView: TextView = cardView.findViewById(R.id.text_breed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_breed, parent, false)
        return CardViewHolder(cardView as CardView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.textView.text = breedList[position]
        holder.cardView.setOnClickListener {
            clickListener.onClick(breedListHyphenated[position], breedListKeyword[position])
        }
    }

    fun update(map: Map<String, MutableList<String>>) {
        extractMap(map)
        notifyDataSetChanged()
    }

    private fun extractMap(map: Map<String, MutableList<String>>) {
        breedList.clear()
        breedListHyphenated.clear()
        breedListKeyword.clear()
        for ((key, value) in map) {
            if (value.isNotEmpty()) {
                value.forEach { v ->
                    breedList.add("$key $v")
                    breedListHyphenated.add("$key-$v")
                    breedListKeyword.add(key)
                }
            } else {
                breedList.add(key)
                breedListHyphenated.add(key)
                breedListKeyword.add(key)
            }
        }
    }

    override fun getItemCount(): Int = breedList.size

    interface ClickListener {
        fun onClick(breed_hyphenated: String, breed_keyword: String)
    }
}