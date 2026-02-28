package ru.otus.cryptosample.coins.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.otus.cryptosample.coins.feature.CoinState
import ru.otus.cryptosample.databinding.ItemCoinHorizontalBinding

class HorizontalCoinsAdapter : ListAdapter<CoinState, CoinHorizontalViewHolder>(CoinDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinHorizontalViewHolder {
        return CoinHorizontalViewHolder(
            ItemCoinHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoinHorizontalViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}