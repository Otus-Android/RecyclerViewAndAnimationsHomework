package ru.otus.cryptosample.coins.feature.coinsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.otus.cryptosample.coins.feature.CoinState
import ru.otus.cryptosample.databinding.ItemCoinBinding

class CoinsInnerAdapter : ListAdapter<CoinState, CoinViewHolder>(CoinsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            ItemCoinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: CoinViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}