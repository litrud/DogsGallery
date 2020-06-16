package com.litrud.dogsgallery.listbreed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.litrud.dogsgallery.R
import java.util.*

class BreedListAdapter : RecyclerView.Adapter<BreedListAdapter.CardViewHolder>() {

    private var breedListFull = mutableListOf<String>()
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
        holder.textView.text = breedListFull[position]
        holder.cardView.setOnClickListener {
            // pass arguments within the action
            val action = BreedListFragmentDirections.actionBreedListFragmentToPhotoListFragment(
                breedListFull[position],
                breedListKeyword[position]
            )
            it.findNavController().navigate(action)
        }
    }

    fun update(map: Map<String, MutableList<String>>) {
        extractMap(map)
        notifyDataSetChanged()
    }

    private fun extractMap(map: Map<String, MutableList<String>>) {
        breedListFull.clear()
        breedListKeyword.clear()
        for ((key, value) in map) {
            if (value.isNotEmpty()) {
                value.forEach { v ->
                    breedListFull.add("$key $v")
                    breedListKeyword.add(key)
                }
            } else {
                breedListFull.add(key)
                breedListKeyword.add(key)
            }
        }
    }

    override fun getItemCount(): Int
            = breedListFull.size
}