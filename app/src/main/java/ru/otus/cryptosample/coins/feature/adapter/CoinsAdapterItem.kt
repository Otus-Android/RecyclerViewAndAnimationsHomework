package ru.otus.cryptosample.coins.feature.adapter

import ru.otus.cryptosample.coins.feature.CoinState

sealed class CoinsAdapterItem {

    data class CategoryHeader(
        val categoryId: String,
        val categoryName: String,
        val showHorizontal: Boolean = false
    ) : CoinsAdapterItem()

    data class CoinItem(val coin: CoinState) : CoinsAdapterItem()

    data class HorizontalCoinList(
        val categoryId: String,
        val coins: List<CoinState>,
        val showAll: Boolean = false
    ) : CoinsAdapterItem()

}