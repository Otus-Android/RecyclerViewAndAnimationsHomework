package ru.otus.cryptosample.coins.feature

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class FadeSlideItemAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {

        dispatchAddStarting(holder)

        holder.itemView.apply {
            alpha = 0f
            translationX = -50f
            animate()
                .alpha(1f)
                .translationX(0f)
                .setDuration(250)
                .withEndAction {
                    dispatchAddFinished(holder)
                }
                .start()
        }

        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {

        dispatchRemoveStarting(holder)

        holder.itemView.animate()
            .alpha(0f)
            .translationX(50f)
            .setDuration(250)
            .withEndAction {
                dispatchRemoveFinished(holder)
            }
            .start()

        return true
    }
}
