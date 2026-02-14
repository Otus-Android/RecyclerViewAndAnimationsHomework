package ru.otus.cryptosample.coins.feature.adapter

import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class ScaleItemAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.apply {
            scaleX = 0.7f
            scaleY = 0.7f
            alpha = 0f

            animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(addDuration)
                .setInterpolator(OvershootInterpolator(1.5f))
                .start()
        }
        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.animate()
            .scaleX(0.5f)
            .scaleY(0.5f)
            .alpha(0f)
            .setDuration(removeDuration)
            .setInterpolator(AccelerateInterpolator())
            .start()
        return true
    }
}