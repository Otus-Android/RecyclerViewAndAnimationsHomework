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
        holder.itemView.scaleY = 0.95f
        return super.animateAdd(holder)
    }

    override fun onAddFinished(item: RecyclerView.ViewHolder) {
        item.itemView.scaleY = 1f
    }

    override fun onRemoveFinished(item: RecyclerView.ViewHolder) {
        item.itemView.scaleY = 1f
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        super.endAnimation(item)
        item.itemView.scaleY = 1f
    }
}