package ru.otus.cryptosample.coins.feature.adapter

import androidx.recyclerview.widget.DiffUtil

class CoinsDiffUtil(
    private val oldList: List<CoinsAdapterItem>,
    private val newList: List<CoinsAdapterItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when {
            oldItem is CoinsAdapterItem.CategoryHeader &&
                    newItem is CoinsAdapterItem.CategoryHeader ->
                oldItem.categoryId == newItem.categoryId

            oldItem is CoinsAdapterItem.CoinItem &&
                    newItem is CoinsAdapterItem.CoinItem ->
                oldItem.coin.id == newItem.coin.id

            oldItem is CoinsAdapterItem.HorizontalCoinList &&
                    newItem is CoinsAdapterItem.HorizontalCoinList ->
                oldItem.categoryId == newItem.categoryId

            else -> false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem is CoinsAdapterItem.CoinItem && newItem is CoinsAdapterItem.CoinItem) {
            val payload = mutableListOf<String>()
            if (oldItem.coin.highlight != newItem.coin.highlight) {
                payload.add("highlight")
            }
            if (oldItem.coin.price != newItem.coin.price) {
                payload.add("price")
            }
            if (oldItem.coin.discount != newItem.coin.discount) {
                payload.add("discount")
            }
            return payload.ifEmpty { null }
        }

        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    companion object {
        const val HIGHLIGHT_PAYLOAD = "highlight"
        const val PRICE_PAYLOAD = "price"
        const val DISCOUNT_PAYLOAD = "discount"
    }

}