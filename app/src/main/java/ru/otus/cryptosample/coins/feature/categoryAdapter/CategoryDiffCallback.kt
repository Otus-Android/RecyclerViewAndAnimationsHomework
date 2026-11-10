package ru.otus.cryptosample.coins.feature.categoryAdapter

import androidx.recyclerview.widget.DiffUtil
import ru.otus.cryptosample.coins.feature.CoinCategoryState

class CategoryDiffCallback: DiffUtil.ItemCallback<CoinCategoryState>() {
    override fun areItemsTheSame(
        oldItem: CoinCategoryState,
        newItem: CoinCategoryState
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CoinCategoryState,
        newItem: CoinCategoryState
    ): Boolean {
        return oldItem == newItem
    }
}