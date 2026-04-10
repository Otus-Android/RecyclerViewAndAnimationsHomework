package ru.otus.cryptosample.coins.feature.adapter

import ru.otus.cryptosample.coins.feature.CoinState

sealed class CoinsAdapterItem(val id: String) {
    data class CategoryHeader(val categoryId: String, val categoryName: String) : CoinsAdapterItem(categoryId)
    data class CoinItem(val coin: CoinState) : CoinsAdapterItem(coin.id)
    data class HorizontalCategory(val categoryId: String, val coins: List<CoinState>) : CoinsAdapterItem(categoryId + "_horizontal")
}