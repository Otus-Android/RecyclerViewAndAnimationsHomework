package ru.otus.cryptosample.coins.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.R
import ru.otus.cryptosample.coins.feature.CoinState

class HorizontalCategoryViewHolder(
    parent: ViewGroup,
    private val sharedViewPool: RecyclerView.RecycledViewPool
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal_category, parent, false)
) {

    private val recyclerView: RecyclerView = itemView.findViewById(R.id.horizontalRecyclerView)
    private val adapter = HorizontalCoinsAdapter()

    init {
        recyclerView.adapter = adapter
        recyclerView.setRecycledViewPool(sharedViewPool)
        recyclerView.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
    }

    fun bind(coins: List<CoinState>) {
        adapter.submitList(coins)
    }
}
