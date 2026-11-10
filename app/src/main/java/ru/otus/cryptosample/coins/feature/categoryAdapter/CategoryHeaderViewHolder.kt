package ru.otus.cryptosample.coins.feature.categoryAdapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinCategoryState
import ru.otus.cryptosample.coins.feature.coinsAdapter.CoinsInnerAdapter
import ru.otus.cryptosample.databinding.ItemCategoryHeaderBinding

class CategoryHeaderViewHolder(private val binding: ItemCategoryHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val coinsAdapter = CoinsInnerAdapter()

    private var currentLayoutIsHorizontal: Boolean? = null

    init {
        binding.coinsRecyclerView.adapter = coinsAdapter
        binding.coinsRecyclerView.isNestedScrollingEnabled = false
    }

    fun bind(
        category: CoinCategoryState,
        showAll: Boolean
    ) {
        binding.categoryTitle.text = category.name

        val isHorizontal = showAll && category.coins.size > 10

        if (currentLayoutIsHorizontal != isHorizontal) {

            val gridLayoutManager =
                if (isHorizontal) {
                    GridLayoutManager(
                        binding.root.context,
                        1,
                        GridLayoutManager.HORIZONTAL,
                        false
                    )
                } else {
                    GridLayoutManager(
                        binding.root.context,
                        2
                    )
                }

            binding.coinsRecyclerView.layoutManager = gridLayoutManager

            currentLayoutIsHorizontal = isHorizontal
        }

        coinsAdapter.submitList(category.coins)
    }
}