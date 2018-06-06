package com.demo.hearthstone

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.view.LayoutInflater
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_card_layout.view.*

class GridAdapter(private val mContext: Context, private val mCards: List<Models.Card>) : BaseAdapter() {
    override fun getCount(): Int = mCards.size

    override fun getItem(position: Int): Any? = mCards[position]

    override fun getItemId(position: Int): Long = 0L

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val gridView: View
        gridView = inflater.inflate(R.layout.grid_card_layout, null)
        gridView.tvCardName.text = mCards[p0].name
        gridView.tvCardPlayerClass.text = mCards[p0].playerClass
        gridView.tvCardType.text = mCards[p0].type
        if (mCards[p0].img.isNotEmpty()) {
            Picasso.get().load(mCards[p0].img).into(gridView.ivCardImage)
        } else {
            gridView.ivCardImage.setImageResource(R.drawable.ic_placeholder)
        }
        return gridView
    }
}