package ru.otus.cryptosample.coins.feature.coinsAdapter

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class CoinsItemAnimator : DefaultItemAnimator() {

    init {
        addDuration = 300
        removeDuration = 300
        moveDuration = 250
        changeDuration = 250
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.apply {
            alpha = 0f
            scaleY = 0.95f
        }
        return super.animateAdd(holder)
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.apply {
            alpha = 0f
            scaleY = 0.95f
        }
        return super.animateRemove(holder)
    }
}