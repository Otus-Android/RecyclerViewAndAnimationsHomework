package ru.otus.cryptosample.coins.feature.adapter

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.otus.cryptosample.R
import ru.otus.cryptosample.coins.feature.CoinState
import ru.otus.cryptosample.databinding.ItemCoinBinding
import ru.otus.cryptosample.databinding.ItemCoinHorizontalListBinding

class CoinsHorizontalViewHolder(
    private val binding: ItemCoinHorizontalListBinding,
   private val sharedPool: RecyclerView.RecycledViewPool
) : RecyclerView.ViewHolder(binding.root) {

    private val adapter = CoinHorizontalAdapter()

    init {
        binding.recyclerHorizontal.layoutManager =
            LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

        binding.recyclerHorizontal.adapter = adapter
        //binding.recyclerHorizontal.setRecycledViewPool(sharedPool) тут краш ClassCastException
    }

    fun bind(items: List<CoinState>) {
        adapter.submitList(items)
    }
}