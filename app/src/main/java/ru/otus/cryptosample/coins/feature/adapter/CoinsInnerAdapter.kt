package ru.otus.cryptosample.coins.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.coins.feature.CoinState
import ru.otus.cryptosample.databinding.ItemCoinBinding

class CoinsInnerAdapter : RecyclerView.Adapter<CoinViewHolder>() {

    private var coins = emptyList<CoinState>()

    fun setData(newCoins: List<CoinState>) {
        coins = newCoins
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = coins.size

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
        holder.bind(coins[position])
    }
}