package ru.otus.cryptosample.coins.feature.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.otus.cryptosample.coins.feature.CoinState

class CoinDiffCallback : DiffUtil.ItemCallback<CoinState>() {
    override fun areItemsTheSame(oldItem: CoinState, newItem: CoinState): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CoinState, newItem: CoinState): Boolean {
        return oldItem == newItem
    }
}