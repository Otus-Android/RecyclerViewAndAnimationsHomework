package ru.otus.cryptosample.coins.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.otus.cryptosample.coins.feature.CoinState
import ru.otus.cryptosample.databinding.ItemCoinBinding

class HorizontalCoinsAdapter : ListAdapter<CoinState, CoinViewHolder>(CoinDiffCallback) {

    companion object {
        const val PAYLOAD_HIGHLIGHT = "PAYLOAD_HIGHLIGHT"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val layoutParams = binding.root.layoutParams

        layoutParams.width = (140 * parent.context.resources.displayMetrics.density).toInt()
        binding.root.layoutParams = layoutParams
        
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: CoinViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            if (payloads.contains(PAYLOAD_HIGHLIGHT)) {
                holder.bindPayload(getItem(position))
            }
        }
    }

    object CoinDiffCallback : DiffUtil.ItemCallback<CoinState>() {
        override fun areItemsTheSame(oldItem: CoinState, newItem: CoinState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoinState, newItem: CoinState): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: CoinState, newItem: CoinState): Any? {
            if (oldItem.copy(highlight = newItem.highlight) == newItem) {
                return PAYLOAD_HIGHLIGHT
            }
            return super.getChangePayload(oldItem, newItem)
        }
    }
}
