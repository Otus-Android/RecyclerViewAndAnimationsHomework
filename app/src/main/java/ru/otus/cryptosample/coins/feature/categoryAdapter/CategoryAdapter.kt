package ru.otus.cryptosample.coins.feature.categoryAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinCategoryState
import ru.otus.cryptosample.databinding.ItemCategoryHeaderBinding

class CategoryAdapter :
    ListAdapter<CoinCategoryState, CategoryHeaderViewHolder>(CategoryDiffCallback()) {
    private var showAll = false

    private val sharedPool = RecyclerView.RecycledViewPool()

    fun submitList(
        newList: List<CoinCategoryState>,
        showAll: Boolean
    ) {
        this.showAll = showAll
        submitList(newList)
    }

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
        holder.bind(
            getItem(position),
            showAll
        )
    }
}