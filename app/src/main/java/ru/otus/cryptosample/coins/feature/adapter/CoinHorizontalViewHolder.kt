package ru.otus.cryptosample.coins.feature.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.otus.cryptosample.R
import ru.otus.cryptosample.coins.feature.CoinState
import ru.otus.cryptosample.databinding.ItemCoinHorizontalBinding

class CoinHorizontalViewHolder(
    private val binding: ItemCoinHorizontalBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(coin: CoinState) {
        with(binding) {
            coinName.text = coin.name
            coinPrice.text = coin.price
            coinChange.text = coin.discount

            val changeColor = if (coin.goesUp) {
                ContextCompat.getColor(itemView.context, android.R.color.holo_red_dark)
            } else {
                ContextCompat.getColor(itemView.context, android.R.color.holo_green_dark)
            }
            coinChange.setTextColor(changeColor)

            coinIcon.load(coin.image) {
                placeholder(R.drawable.generic)
                error(R.drawable.generic)
            }
            
            updateHighlight(coin.highlight)
        }
    }

    fun updateHighlight(isHighlighted: Boolean) {
        binding.fireBadge.visibility = if (isHighlighted) RecyclerView.VISIBLE else RecyclerView.GONE
    }
}