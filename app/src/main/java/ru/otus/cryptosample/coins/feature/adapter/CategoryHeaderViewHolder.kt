package ru.otus.cryptosample.coins.feature.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinCategoryState
import ru.otus.cryptosample.databinding.ItemCategoryHeaderBinding

class CategoryHeaderViewHolder(
    private val binding: ItemCategoryHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

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

            binding.coinsRecyclerView.layoutManager =
                if (isHorizontal) {
                    LinearLayoutManager(
                        binding.root.context,
                        RecyclerView.HORIZONTAL,
                        false
                    )
                } else {
                    GridLayoutManager(
                        binding.root.context,
                        2
                    )
                }

            currentLayoutIsHorizontal = isHorizontal
        }

        coinsAdapter.setData(category.coins)
    }
}