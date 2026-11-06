package ru.otus.cryptosample.coins.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinCategoryState
import ru.otus.cryptosample.databinding.ItemCategoryHeaderBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryHeaderViewHolder>() {
    private var categories = emptyList<CoinCategoryState>()
    private var showAll = false

    fun setData(
        newCategories: List<CoinCategoryState>,
        showAll: Boolean
    ) {
        categories = newCategories
        this.showAll = showAll
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = categories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHeaderViewHolder {

        return CategoryHeaderViewHolder(
            ItemCategoryHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryHeaderViewHolder, position: Int) {
        holder.bind(
            categories[position],
            showAll
        )
    }
}