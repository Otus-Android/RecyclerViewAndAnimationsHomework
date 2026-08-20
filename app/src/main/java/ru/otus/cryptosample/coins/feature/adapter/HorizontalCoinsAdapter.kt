package ru.otus.cryptosample.coins.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.otus.cryptosample.coins.feature.CoinState
import ru.otus.cryptosample.databinding.ItemCoinHorizontalBinding

class HorizontalCoinsAdapter : ListAdapter<CoinState, CoinViewHolder>(CoinDiffCallback) {

    override fun getItemViewType(position: Int): Int = CoinsAdapter.HORIZONTAL_COIN_VIEW_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = ItemCoinHorizontalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return CoinViewHolder(
            binding = binding.coinContent,
            root = binding.root,
        )
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: CoinViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        val highlightPayload = payloads.lastOrNull() as? HighlightPayload
        if (highlightPayload != null) {
            holder.bindHighlight(highlightPayload.isHighlighted)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}
