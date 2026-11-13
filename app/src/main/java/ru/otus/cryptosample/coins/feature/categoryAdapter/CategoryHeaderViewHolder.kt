package ru.otus.cryptosample.coins.feature.categoryAdapter

import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinCategoryState
import ru.otus.cryptosample.coins.feature.coinsAdapter.CoinsInnerAdapter
import ru.otus.cryptosample.coins.feature.coinsAdapter.CoinsItemAnimator
import ru.otus.cryptosample.coins.feature.coinsAdapter.CoinsLayoutManager
import ru.otus.cryptosample.databinding.ItemCategoryHeaderBinding

class CategoryHeaderViewHolder(
    private val binding: ItemCategoryHeaderBinding,
    sharedPool: RecyclerView.RecycledViewPool
) : RecyclerView.ViewHolder(binding.root) {

    private val coinsAdapter = CoinsInnerAdapter()

    private var currentLayoutIsHorizontal: Boolean? = null

    init {
        binding.coinsRecyclerView.adapter = coinsAdapter
        binding.coinsRecyclerView.isNestedScrollingEnabled = false
        binding.coinsRecyclerView.setRecycledViewPool(sharedPool)
        binding.coinsRecyclerView.itemAnimator = CoinsItemAnimator()
    }

    fun bind(
        category: CoinCategoryState,
        showAll: Boolean
    ) {
        binding.categoryTitle.text = category.name

        val isHorizontal = showAll && category.coins.size > 10

        if (currentLayoutIsHorizontal != isHorizontal) {
            binding.coinsRecyclerView.layoutManager =
                CoinsLayoutManager(binding.root.context, horizontal = isHorizontal)
            currentLayoutIsHorizontal = isHorizontal
        }

        coinsAdapter.submitList(category.coins)
    }
}