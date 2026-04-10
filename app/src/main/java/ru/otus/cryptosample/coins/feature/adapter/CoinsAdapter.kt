package ru.otus.cryptosample.coins.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinCategoryState
import ru.otus.cryptosample.databinding.ItemCategoryHeaderBinding
import ru.otus.cryptosample.databinding.ItemCoinBinding
import ru.otus.cryptosample.databinding.ItemHorizontalCategoryBinding

class CoinsAdapter : ListAdapter<CoinsAdapterItem, RecyclerView.ViewHolder>(ItemDiffCallback) {
    
    companion object {
        const val VIEW_TYPE_CATEGORY = 0
        const val VIEW_TYPE_COIN = 1
        const val VIEW_TYPE_HORIZONTAL_CATEGORY = 2
        const val PAYLOAD_HIGHLIGHT = "PAYLOAD_HIGHLIGHT"
    }

    private val sharedViewPool = RecyclerView.RecycledViewPool()

    fun setData(categories: List<CoinCategoryState>) {
        val adapterItems = mutableListOf<CoinsAdapterItem>()
        
        categories.forEach { category ->
            adapterItems.add(CoinsAdapterItem.CategoryHeader(category.id, category.name))
            if (category.coins.size > 10) {
                adapterItems.add(CoinsAdapterItem.HorizontalCategory(category.id, category.coins))
            } else {
                category.coins.forEach { coin ->
                    adapterItems.add(CoinsAdapterItem.CoinItem(coin))
                }
            }
        }
        
        submitList(adapterItems)
    }
    
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CoinsAdapterItem.CategoryHeader -> VIEW_TYPE_CATEGORY
            is CoinsAdapterItem.CoinItem -> VIEW_TYPE_COIN
            is CoinsAdapterItem.HorizontalCategory -> VIEW_TYPE_HORIZONTAL_CATEGORY
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_CATEGORY -> CategoryHeaderViewHolder(
                ItemCategoryHeaderBinding.inflate(layoutInflater, parent, false)
            )
            VIEW_TYPE_COIN -> CoinViewHolder(
                ItemCoinBinding.inflate(layoutInflater, parent, false)
            )
            VIEW_TYPE_HORIZONTAL_CATEGORY -> HorizontalCategoryViewHolder(
                ItemHorizontalCategoryBinding.inflate(layoutInflater, parent, false),
                sharedViewPool
            )
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is CoinsAdapterItem.CategoryHeader -> {
                (holder as CategoryHeaderViewHolder).bind(item.categoryName)
            }
            is CoinsAdapterItem.CoinItem -> {
                (holder as CoinViewHolder).bind(item.coin)
            }
            is CoinsAdapterItem.HorizontalCategory -> {
                (holder as HorizontalCategoryViewHolder).bind(item.coins)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val item = getItem(position)
            if (payloads.contains(PAYLOAD_HIGHLIGHT)) {
                when (item) {
                    is CoinsAdapterItem.CoinItem -> {
                        (holder as CoinViewHolder).bindPayload(item.coin)
                    }
                    is CoinsAdapterItem.HorizontalCategory -> {
                        (holder as HorizontalCategoryViewHolder).bind(item.coins)
                    }
                    else -> Unit
                }
            }
        }
    }

    object ItemDiffCallback : DiffUtil.ItemCallback<CoinsAdapterItem>() {
        override fun areItemsTheSame(oldItem: CoinsAdapterItem, newItem: CoinsAdapterItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoinsAdapterItem, newItem: CoinsAdapterItem): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: CoinsAdapterItem, newItem: CoinsAdapterItem): Any? {
            if (oldItem is CoinsAdapterItem.CoinItem && newItem is CoinsAdapterItem.CoinItem) {
                if (oldItem.coin.copy(highlight = newItem.coin.highlight) == newItem.coin) {
                    return PAYLOAD_HIGHLIGHT
                }
            } else if (oldItem is CoinsAdapterItem.HorizontalCategory && newItem is CoinsAdapterItem.HorizontalCategory) {
                return PAYLOAD_HIGHLIGHT
            }
            return super.getChangePayload(oldItem, newItem)
        }
    }
}