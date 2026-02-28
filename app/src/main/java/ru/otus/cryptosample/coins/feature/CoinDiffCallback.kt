package ru.otus.cryptosample.coins.feature

import androidx.recyclerview.widget.DiffUtil
import ru.otus.cryptosample.coins.feature.adapter.CoinsAdapterItem
import ru.otus.cryptosample.coins.feature.adapter.HighlightPayload

class CoinDiffCallback : DiffUtil.ItemCallback<CoinsAdapterItem>() {
    override fun areItemsTheSame(oldItem: CoinsAdapterItem, newItem: CoinsAdapterItem): Boolean {
        return when {
            oldItem is CoinsAdapterItem.CoinItem && newItem is CoinsAdapterItem.CoinItem -> oldItem.coin.id == newItem.coin.id
            oldItem is CoinsAdapterItem.CategoryHeader && newItem is CoinsAdapterItem.CategoryHeader -> oldItem.categoryId == newItem.categoryId
            oldItem is CoinsAdapterItem.HorizontalCategory && newItem is CoinsAdapterItem.HorizontalCategory -> oldItem.categoryId == newItem.categoryId
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: CoinsAdapterItem, newItem: CoinsAdapterItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: CoinsAdapterItem, newItem: CoinsAdapterItem): Any? {
        if (oldItem is CoinsAdapterItem.CoinItem && newItem is CoinsAdapterItem.CoinItem) {
            if (oldItem.coin.highlight != newItem.coin.highlight) return "highlight"
        }
        return null
    }
}

//class CoinDiffCallback : DiffUtil.ItemCallback<CoinState>() {
//
//    override fun areItemsTheSame(
//        oldItem: CoinState,
//        newItem: CoinState
//    ): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(
//        oldItem: CoinState,
//        newItem: CoinState
//    ): Boolean {
//        return oldItem == newItem
//    }
//
//    override fun getChangePayload(
//        oldItem: CoinState,
//        newItem: CoinState
//    ): Any? {
//        if (oldItem.highlight != newItem.highlight) {
//            return HighlightPayload
//        }
//        return null
//    }
//}
