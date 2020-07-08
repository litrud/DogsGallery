package com.litrud.dogsgallery.listbreed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.litrud.dogsgallery.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_breed.*


class BreedListAdapter : RecyclerView.Adapter<BreedListAdapter.CardViewHolder>() {

    private var breedListFull = mutableListOf<String>()
    private var breedListKeyword = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_breed, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindItem(position)
    }

    override fun getItemCount(): Int
            = breedListFull.size

    inner class CardViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(position: Int) {
            text_breed.text = breedListFull[position]

            containerView.setOnClickListener {
                // pass arguments within the action
                val action = BreedListFragmentDirections.actionBreedListFragmentToPhotoListFragment(
                    breedListFull[position],
                    breedListKeyword[position]
                )
                it.findNavController().navigate(action)
            }
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
}