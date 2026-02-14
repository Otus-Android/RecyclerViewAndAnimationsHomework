package ru.otus.cryptosample.coins.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinState
import ru.otus.cryptosample.databinding.ItemCoinHorizontalItemBinding

class HorizontalCoinsAdapter : RecyclerView.Adapter<HorizontalCoinViewHolder>() {

    private var coins = emptyList<CoinState>()

    fun setData(newCoins: List<CoinState>) {
        val diffResult = DiffUtil.calculateDiff(
            object : DiffUtil.Callback() {
                override fun getOldListSize(): Int = coins.size
                override fun getNewListSize(): Int = newCoins.size

                override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
                    return coins[oldPos].id == newCoins[newPos].id
                }

                override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
                    return coins[oldPos] == newCoins[newPos]
                }

                override fun getChangePayload(oldPos: Int, newPos: Int): Any? {
                    val oldCoin = coins[oldPos]
                    val newCoin = newCoins[newPos]

                    val payload = mutableListOf<String>()
                    if (oldCoin.highlight != newCoin.highlight) {
                        payload.add(CoinsDiffUtil.HIGHLIGHT_PAYLOAD)
                    }
                    if (oldCoin.price != newCoin.price) {
                        payload.add(CoinsDiffUtil.PRICE_PAYLOAD)
                    }
                    if (oldCoin.discount != newCoin.discount) {
                        payload.add(CoinsDiffUtil.DISCOUNT_PAYLOAD)
                    }
                    return payload.ifEmpty { null }
                }
            }
        )
        coins = newCoins
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalCoinViewHolder {
        val binding = ItemCoinHorizontalItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HorizontalCoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalCoinViewHolder, position: Int) {
        holder.bind(coins[position])
    }

    override fun onBindViewHolder(
        holder: HorizontalCoinViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isNotEmpty()) {
            holder.bindWithPayload(coins[position], payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int = coins.size

}