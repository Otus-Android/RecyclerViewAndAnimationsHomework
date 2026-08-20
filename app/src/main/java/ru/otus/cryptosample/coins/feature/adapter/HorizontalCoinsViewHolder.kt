package ru.otus.cryptosample.coins.feature.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinState
import ru.otus.cryptosample.databinding.ItemHorizontalCoinListBinding

class HorizontalCoinsViewHolder(
    private val binding: ItemHorizontalCoinListBinding,
    sharedViewPool: RecyclerView.RecycledViewPool,
) : RecyclerView.ViewHolder(binding.root) {

    private val coinsAdapter = HorizontalCoinsAdapter()
    private var boundCategoryId: String? = null

    init {
        binding.horizontalCoins.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false,
            ).apply {
                initialPrefetchItemCount = 4
            }
            adapter = coinsAdapter
            setRecycledViewPool(sharedViewPool)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }
    }

    fun bind(categoryId: String, coins: List<CoinState>) {
        if (boundCategoryId != categoryId) {
            binding.horizontalCoins.scrollToPosition(0)
            boundCategoryId = categoryId
        }
        bindCoins(coins)
    }

    fun bindCoins(coins: List<CoinState>) {
        coinsAdapter.submitList(coins)
    }
}
