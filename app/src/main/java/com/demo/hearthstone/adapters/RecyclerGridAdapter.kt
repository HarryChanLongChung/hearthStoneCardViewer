package com.demo.hearthstone.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demo.hearthstone.datamodels.Models
import com.demo.hearthstone.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_card_layout.view.*


class RecyclerGridAdapter(private val mContext: Context, private val mCards: List<Models.Card>) : RecyclerView.Adapter<RecyclerGridAdapter.CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(LayoutInflater.from(mContext).inflate(R.layout.grid_card_layout, parent, false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.setCardInfo(mCards[position])
    }

    override fun getItemCount(): Int = mCards.size

    open inner class CardViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setCardInfo(card: Models.Card) {
            itemView.tvCardName.text = card.name
            itemView.tvCardPlayerClass.text = card.playerClass
            itemView.tvCardType.text = card.type

            if (card.img.isNotEmpty()) {
                Picasso.get().load(card.img).into(itemView.ivCardImage)
            } else {
                itemView.ivCardImage.setImageResource(R.drawable.ic_placeholder)
            }
        }
    }
}