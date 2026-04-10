package ru.otus.cryptosample.coins.feature.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinState
import ru.otus.cryptosample.databinding.ItemHorizontalCategoryBinding

class HorizontalCategoryViewHolder(
    private val binding: ItemHorizontalCategoryBinding,
    viewPool: RecyclerView.RecycledViewPool
) : RecyclerView.ViewHolder(binding.root) {

    private val adapter = HorizontalCoinsAdapter()

    init {
        binding.horizontalRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            binding.root.context,
            RecyclerView.HORIZONTAL,
            false
        )
        binding.horizontalRecyclerView.setRecycledViewPool(viewPool)
        binding.horizontalRecyclerView.adapter = adapter
    }

    fun bind(coins: List<CoinState>) {
        itemView.alpha = 1f
        itemView.scaleX = 1f
        itemView.scaleY = 1f
        itemView.translationY = 0f
        
        adapter.submitList(coins)
    }
}
