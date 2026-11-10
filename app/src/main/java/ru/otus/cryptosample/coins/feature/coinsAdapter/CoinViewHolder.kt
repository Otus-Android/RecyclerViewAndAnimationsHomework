package ru.otus.cryptosample.coins.feature.coinsAdapter

import android.R
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.otus.cryptosample.coins.feature.CoinState
import ru.otus.cryptosample.databinding.ItemCoinBinding

class CoinViewHolder(
    private val binding: ItemCoinBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(coin: CoinState) {
        with(binding) {
            coinName.text = coin.name
            coinPrice.text = coin.price
            coinChange.text = coin.discount

            val changeColor = if (coin.goesUp) {
                ContextCompat.getColor(itemView.context, R.color.holo_red_dark)
            } else {
                ContextCompat.getColor(itemView.context, R.color.holo_green_dark)
            }
            coinChange.setTextColor(changeColor)

            coinIcon.load(coin.image) {
                placeholder(ru.otus.cryptosample.R.drawable.generic)
                error(ru.otus.cryptosample.R.drawable.generic)
            }

            fireBadge.isVisible = coin.highlight
        }
    }
}