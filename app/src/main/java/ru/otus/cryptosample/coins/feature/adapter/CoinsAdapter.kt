package ru.otus.cryptosample.coins.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinCategoryState
import ru.otus.cryptosample.databinding.ItemCategoryHeaderBinding
import ru.otus.cryptosample.databinding.ItemCoinBinding
import ru.otus.cryptosample.databinding.ItemHorizontalCoinListBinding


class CoinsAdapter(
    private val sharedViewPool: RecyclerView.RecycledViewPool,
) : ListAdapter<CoinsAdapterItem, RecyclerView.ViewHolder>(CoinsItemDiffCallback) {

    companion object {
        const val HORIZONTAL_COIN_VIEW_TYPE = 3

        private const val VIEW_TYPE_CATEGORY = 0
        private const val VIEW_TYPE_COIN = 1
        private const val VIEW_TYPE_HORIZONTAL_COIN_LIST = 2

        private const val HORIZONTAL_LIST_THRESHOLD = 10
        private const val COLLAPSED_COIN_COUNT = 4
    }

    fun submitData(categories: List<CoinCategoryState>, showAll: Boolean) {
        val adapterItems = buildList {
            categories.forEach { category ->
                add(
                    CoinsAdapterItem.CategoryHeader(
                        categoryId = category.id,
                        categoryName = category.name,
                    )
                )

                if (showAll && category.coins.size > HORIZONTAL_LIST_THRESHOLD) {
                    add(
                        CoinsAdapterItem.HorizontalCoinList(
                            categoryId = category.id,
                            coins = category.coins,
                        )
                    )
                } else {
                    val coins = if (showAll) {
                        category.coins
                    } else {
                        category.coins.take(COLLAPSED_COIN_COUNT)
                    }
                    coins.forEach { coin ->
                        add(
                            CoinsAdapterItem.CoinItem(
                                categoryId = category.id,
                                coin = coin,
                            )
                        )
                    }
                }
            }
        }

        submitList(adapterItems)
    }

    fun isFullSpan(position: Int): Boolean =
        getItemViewType(position) != VIEW_TYPE_COIN

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CoinsAdapterItem.CategoryHeader -> VIEW_TYPE_CATEGORY
            is CoinsAdapterItem.CoinItem -> VIEW_TYPE_COIN
            is CoinsAdapterItem.HorizontalCoinList -> VIEW_TYPE_HORIZONTAL_COIN_LIST
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
            VIEW_TYPE_HORIZONTAL_COIN_LIST -> HorizontalCoinsViewHolder(
                binding = ItemHorizontalCoinListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                sharedViewPool = sharedViewPool,
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
            is CoinsAdapterItem.HorizontalCoinList -> {
                (holder as HorizontalCoinsViewHolder).bind(
                    categoryId = item.categoryId,
                    coins = item.coins,
                )
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }

        val lastPayload = payloads.last()
        when {
            holder is CoinViewHolder && lastPayload is HighlightPayload -> {
                holder.bindHighlight(lastPayload.isHighlighted)
            }
            holder is HorizontalCoinsViewHolder && lastPayload is HorizontalCoinsPayload -> {
                holder.bindCoins(lastPayload.coins)
            }
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }

    private object CoinsItemDiffCallback : DiffUtil.ItemCallback<CoinsAdapterItem>() {
        override fun areItemsTheSame(
            oldItem: CoinsAdapterItem,
            newItem: CoinsAdapterItem,
        ): Boolean {
            return when {
                oldItem is CoinsAdapterItem.CategoryHeader &&
                    newItem is CoinsAdapterItem.CategoryHeader -> {
                    oldItem.categoryId == newItem.categoryId
                }
                oldItem is CoinsAdapterItem.CoinItem &&
                    newItem is CoinsAdapterItem.CoinItem -> {
                    oldItem.categoryId == newItem.categoryId &&
                        oldItem.coin.id == newItem.coin.id
                }
                oldItem is CoinsAdapterItem.HorizontalCoinList &&
                    newItem is CoinsAdapterItem.HorizontalCoinList -> {
                    oldItem.categoryId == newItem.categoryId
                }
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: CoinsAdapterItem,
            newItem: CoinsAdapterItem,
        ): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(
            oldItem: CoinsAdapterItem,
            newItem: CoinsAdapterItem,
        ): Any? {
            return when {
                oldItem is CoinsAdapterItem.CoinItem &&
                    newItem is CoinsAdapterItem.CoinItem -> {
                    coinHighlightPayload(oldItem.coin, newItem.coin)
                }
                oldItem is CoinsAdapterItem.HorizontalCoinList &&
                    newItem is CoinsAdapterItem.HorizontalCoinList -> {
                    HorizontalCoinsPayload(newItem.coins)
                }
                else -> null
            }
        }
    }
}
