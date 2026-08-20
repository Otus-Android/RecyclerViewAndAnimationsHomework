package ru.otus.cryptosample.coins.feature.adapter

import ru.otus.cryptosample.coins.feature.CoinState

sealed interface CoinsAdapterItem {
    data class CategoryHeader(
        val categoryId: String,
        val categoryName: String,
    ) : CoinsAdapterItem

    data class CoinItem(
        val categoryId: String,
        val coin: CoinState,
    ) : CoinsAdapterItem

    data class HorizontalCoinList(
        val categoryId: String,
        val coins: List<CoinState>,
    ) : CoinsAdapterItem
}
