package ru.otus.cryptosample.coins.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinCategoryState
import ru.otus.cryptosample.databinding.ItemCategoryHeaderBinding
import ru.otus.cryptosample.databinding.ItemCoinBinding

class CoinsAdapter(
    private val sharedViewPool: RecyclerView.RecycledViewPool
) : ListAdapter<CoinsAdapterItem, RecyclerView.ViewHolder>(CoinsDiffCallback()) {

    init {
        setHasStableIds(true)
    }

    companion object {
        private const val VIEW_TYPE_CATEGORY = 0
        private const val VIEW_TYPE_COIN = 1
        private const val VIEW_TYPE_HORIZONTAL = 2
    }

    fun setData(categories: List<CoinCategoryState>) {
        val adapterItems = mutableListOf<CoinsAdapterItem>()
        categories.forEach { category ->
            adapterItems.add(CoinsAdapterItem.CategoryHeader(category.id, category.name))

            if (category.coins.size > 10) {
                adapterItems.add(
                    CoinsAdapterItem.HorizontalCategory(category.id, category.coins)
                )
            } else {
                category.coins.forEach { coin ->
                    adapterItems.add(CoinsAdapterItem.CoinItem(coin))
                }
            }
        }
        submitList(adapterItems)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CoinsAdapterItem.CategoryHeader -> VIEW_TYPE_CATEGORY
            is CoinsAdapterItem.CoinItem -> VIEW_TYPE_COIN
            is CoinsAdapterItem.HorizontalCategory -> VIEW_TYPE_HORIZONTAL
            else -> throw java.lang.IllegalArgumentException("Unknown view type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CATEGORY -> CategoryHeaderViewHolder(
                ItemCategoryHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            VIEW_TYPE_COIN -> CoinViewHolder(
                ItemCoinBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            VIEW_TYPE_HORIZONTAL -> HorizontalCategoryViewHolder(
                parent, sharedViewPool
            )
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoinViewHolder -> {
                val item = getItem(position) as? CoinsAdapterItem.CoinItem ?: return
                holder.bind(item.coin)
            }
            is CategoryHeaderViewHolder -> {
                val item = getItem(position) as? CoinsAdapterItem.CategoryHeader ?: return
                holder.bind(item.categoryName)
            }
            is HorizontalCategoryViewHolder -> {
                val item = getItem(position) as? CoinsAdapterItem.HorizontalCategory ?: return
                holder.bind(item.coins)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty() && holder is CoinViewHolder && getItem(position) is CoinsAdapterItem.CoinItem) {
            val coinItem = getItem(position) as CoinsAdapterItem.CoinItem
            coinItem.coin.highlight.let { holder.updateHighlight(it) }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}

class CoinsDiffCallback : DiffUtil.ItemCallback<CoinsAdapterItem>() {

    override fun areItemsTheSame(
        oldItem: CoinsAdapterItem,
        newItem: CoinsAdapterItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CoinsAdapterItem,
        newItem: CoinsAdapterItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItem: CoinsAdapterItem,
        newItem: CoinsAdapterItem
    ): Any? {
        if (
            oldItem is CoinsAdapterItem.CoinItem &&
            newItem is CoinsAdapterItem.CoinItem
        ) {
            if (oldItem.coin.highlight != newItem.coin.highlight) {
                return HighlightPayload
            }
        }
        return null
    }
}

object HighlightPayload