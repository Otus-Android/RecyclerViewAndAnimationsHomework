package ru.otus.cryptosample.coins.feature.coinsAdapter

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CoinsLayoutManager(
    context: Context,
    private val horizontal: Boolean,
    private val visibleCards: Int = 2
) : GridLayoutManager(
    context,
    if (horizontal) 1 else visibleCards,
    if (horizontal) RecyclerView.HORIZONTAL else RecyclerView.VERTICAL,
    false
) {
    private fun targetWidth(lp: RecyclerView.LayoutParams): Int {
        if (!horizontal || width == 0) return ViewGroup.LayoutParams.MATCH_PARENT
        val space = width - paddingStart - paddingEnd
        return space / visibleCards - lp.marginStart - lp.marginEnd
    }

    override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean =
        super.checkLayoutParams(lp) && lp != null && lp.width == targetWidth(lp)

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?): RecyclerView.LayoutParams =
        super.generateLayoutParams(lp).also { it.width = targetWidth(it) }

    override fun generateLayoutParams(
        c: Context?, attrs: AttributeSet?
    ): RecyclerView.LayoutParams =
        super.generateLayoutParams(c, attrs).also { it.width = targetWidth(it) }
}