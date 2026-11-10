package ru.otus.cryptosample.coins.feature.categoryAdapter

import androidx.recyclerview.widget.DiffUtil
import ru.otus.cryptosample.coins.feature.CategoryUiState

class CategoryDiffCallback: DiffUtil.ItemCallback<CategoryUiState>() {
    override fun areItemsTheSame(
        oldItem: CategoryUiState,
        newItem: CategoryUiState
    ): Boolean {
        return oldItem.category.id == newItem.category.id
    }

    override fun areContentsTheSame(
        oldItem: CategoryUiState,
        newItem: CategoryUiState
    ): Boolean {
        return oldItem == newItem
    }
}