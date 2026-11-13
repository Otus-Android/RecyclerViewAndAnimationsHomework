package ru.otus.cryptosample.coins.feature.categoryAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CategoryUiState
import ru.otus.cryptosample.databinding.ItemCategoryHeaderBinding

class CategoryAdapter :
    ListAdapter<CategoryUiState, CategoryHeaderViewHolder>(CategoryDiffCallback()) {

    private val sharedPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHeaderViewHolder {
        return CategoryHeaderViewHolder(
            ItemCategoryHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            sharedPool
        )
    }

    override fun onBindViewHolder(holder: CategoryHeaderViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(
            item.category,
            item.showAll
        )
    }
}