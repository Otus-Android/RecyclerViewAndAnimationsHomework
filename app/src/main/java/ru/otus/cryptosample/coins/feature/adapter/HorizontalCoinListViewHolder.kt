package ru.otus.cryptosample.coins.feature.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.otus.cryptosample.databinding.ItemCoinHorizontalBinding

class HorizontalCoinListViewHolder(
    binding: ItemCoinHorizontalBinding,
    private val sharedViewPool: RecyclerView.RecycledViewPool
) : RecyclerView.ViewHolder(binding.root) {

    private val horizontalAdapter = HorizontalCoinsAdapter()

    init {
        binding.horizontalRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = horizontalAdapter
            setRecycledViewPool(sharedViewPool)
            setHasFixedSize(true)
        }
    }

    fun bind(item: CoinsAdapterItem.HorizontalCoinList) {
        horizontalAdapter.setData(item.coins)
    }

    fun bindWithPayload(item: CoinsAdapterItem.HorizontalCoinList, payloads: List<Any>) {
        // Если есть payload, обновляем адаптер
        if (payloads.isNotEmpty()) {
            horizontalAdapter.setData(item.coins)
        }
    }

}