package ru.otus.cryptosample.coins.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinCategoryState
import ru.otus.cryptosample.databinding.ItemCategoryHeaderBinding
import ru.otus.cryptosample.databinding.ItemCoinBinding
import ru.otus.cryptosample.databinding.ItemCoinHorizontalBinding

class CoinsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    
    companion object {
        private const val VIEW_TYPE_CATEGORY_HEADER = 0
        private const val VIEW_TYPE_COIN_ITEM = 1
        private const val VIEW_TYPE_HORIZONTAL_LIST = 2

        // Shared ViewPool для горизонтальных списков
        private val horizontalViewPool = RecyclerView.RecycledViewPool()
    }
    
    private var items = listOf<CoinsAdapterItem>()
    
    fun setData(categories: List<CoinCategoryState>) {
        val adapterItems = mutableListOf<CoinsAdapterItem>()
        
        categories.forEach { category ->
            adapterItems.add(
                CoinsAdapterItem.CategoryHeader(
                    categoryId = category.id,
                    categoryName = category.name,
                    showHorizontal = category.showHorizontal
                )
            )

            if (category.showHorizontal) {
                // Для категорий с горизонтальным списком
                adapterItems.add(
                    CoinsAdapterItem.HorizontalCoinList(
                        categoryId = category.id,
                        coins = category.coins,
                    )
                )
            } else {
                // Для обычного отображения (вертикальный список)
                category.coins.forEach { coin ->
                    adapterItems.add(CoinsAdapterItem.CoinItem(coin))
                }
            }
        }

        val diffResult = DiffUtil.calculateDiff(
            CoinsDiffUtil(items, adapterItems)
        )
        items = adapterItems
        diffResult.dispatchUpdatesTo(this)
    }
    
    override fun getItemCount(): Int = items.size
    
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is CoinsAdapterItem.CategoryHeader -> VIEW_TYPE_CATEGORY_HEADER
            is CoinsAdapterItem.CoinItem -> VIEW_TYPE_COIN_ITEM
            is CoinsAdapterItem.HorizontalCoinList -> VIEW_TYPE_HORIZONTAL_LIST
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_CATEGORY_HEADER -> CategoryHeaderViewHolder(
                ItemCategoryHeaderBinding.inflate(inflater, parent, false)
            )
            VIEW_TYPE_COIN_ITEM -> CoinViewHolder(
                ItemCoinBinding.inflate(inflater, parent, false)
            )
            VIEW_TYPE_HORIZONTAL_LIST -> HorizontalCoinListViewHolder(
                ItemCoinHorizontalBinding.inflate(inflater, parent, false),
                horizontalViewPool
            )
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is CoinsAdapterItem.CategoryHeader -> {
                (holder as CategoryHeaderViewHolder).bind(item.categoryName)
            }
            is CoinsAdapterItem.CoinItem -> {
                (holder as CoinViewHolder).bind(item.coin)
            }
            is CoinsAdapterItem.HorizontalCoinList -> {
                (holder as HorizontalCoinListViewHolder).bind(item)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val item = items[position]

            when {
                holder is CoinViewHolder && item is CoinsAdapterItem.CoinItem -> {
                    holder.bindWithPayload(item.coin, payloads)
                }
                holder is HorizontalCoinListViewHolder && item is CoinsAdapterItem.HorizontalCoinList -> {
                    holder.bindWithPayload(item, payloads)
                }
                else -> {
                    super.onBindViewHolder(holder, position, payloads)
                }
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}