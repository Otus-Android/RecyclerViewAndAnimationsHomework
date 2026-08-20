package ru.otus.cryptosample.coins.feature.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.otus.cryptosample.coins.feature.CoinState

data class HighlightPayload(val isHighlighted: Boolean)

data class HorizontalCoinsPayload(val coins: List<CoinState>)

fun coinHighlightPayload(oldItem: CoinState, newItem: CoinState): HighlightPayload? {
    val onlyHighlightChanged = oldItem.highlight != newItem.highlight &&
        oldItem.copy(highlight = newItem.highlight) == newItem

    return if (onlyHighlightChanged) {
        HighlightPayload(newItem.highlight)
    } else {
        null
    }
}

object CoinDiffCallback : DiffUtil.ItemCallback<CoinState>() {
    override fun areItemsTheSame(oldItem: CoinState, newItem: CoinState): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CoinState, newItem: CoinState): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: CoinState, newItem: CoinState): Any? =
        coinHighlightPayload(oldItem, newItem)
}
