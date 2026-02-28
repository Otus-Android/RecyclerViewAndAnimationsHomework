package ru.otus.cryptosample.coins.feature.adapter

import ru.otus.cryptosample.coins.feature.CoinState

sealed class CoinsAdapterItem {
    abstract val id: String

    data class CategoryHeader(
        val categoryId: String,
        val categoryName: String
    ) : CoinsAdapterItem() {
        override val id: String = "header_$categoryId"
    }

    data class CoinItem(
        val coin: CoinState
    ) : CoinsAdapterItem() {
        override val id: String = coin.id
    }

    data class HorizontalCategory(
        val categoryId: String,
        val coins: List<CoinState>
    ) : CoinsAdapterItem() {
        override val id: String = "h_category_$categoryId"
    }
}