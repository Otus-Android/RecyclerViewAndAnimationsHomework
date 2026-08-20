package ru.otus.cryptosample.coins.feature.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

class FadeSlideItemAnimator : SimpleItemAnimator() {

    private enum class AnimationType { ADD, REMOVE }

    private val pendingAdditions = mutableListOf<RecyclerView.ViewHolder>()
    private val pendingRemovals = mutableListOf<RecyclerView.ViewHolder>()
    private val runningAnimations = mutableMapOf<RecyclerView.ViewHolder, AnimationType>()
    private var endingAllAnimations = false

    init {
        addDuration = ANIMATION_DURATION
        removeDuration = ANIMATION_DURATION
        supportsChangeAnimations = false
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        endAnimation(holder)
        holder.itemView.apply {
            alpha = 0f
            translationX = SLIDE_DISTANCE * resources.displayMetrics.density
        }
        pendingAdditions += holder
        return true
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        endAnimation(holder)
        pendingRemovals += holder
        return true
    }

    override fun runPendingAnimations() {
        val removals = pendingRemovals.toList()
        val additions = pendingAdditions.toList()
        pendingRemovals.clear()
        pendingAdditions.clear()

        removals.forEach { startAnimation(it, AnimationType.REMOVE) }
        additions.forEach { startAnimation(it, AnimationType.ADD) }
    }

    private fun startAnimation(
        holder: RecyclerView.ViewHolder,
        type: AnimationType,
    ) {
        val view = holder.itemView
        runningAnimations[holder] = type

        view.animate()
            .alpha(if (type == AnimationType.ADD) 1f else 0f)
            .translationX(
                if (type == AnimationType.ADD) {
                    0f
                } else {
                    -SLIDE_DISTANCE * view.resources.displayMetrics.density
                }
            )
            .setDuration(if (type == AnimationType.ADD) addDuration else removeDuration)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    if (type == AnimationType.ADD) {
                        dispatchAddStarting(holder)
                    } else {
                        dispatchRemoveStarting(holder)
                    }
                }

                override fun onAnimationEnd(animation: Animator) {
                    view.animate().setListener(null)
                    resetView(holder)
                    runningAnimations.remove(holder)

                    if (type == AnimationType.ADD) {
                        dispatchAddFinished(holder)
                    } else {
                        dispatchRemoveFinished(holder)
                    }

                    if (!isRunning()) dispatchAnimationsFinished()
                }
            })
            .start()
    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int,
    ): Boolean {
        dispatchMoveFinished(holder)
        return false
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int,
    ): Boolean {
        if (oldHolder === newHolder) {
            dispatchChangeFinished(oldHolder, true)
        } else {
            dispatchChangeFinished(oldHolder, true)
            dispatchChangeFinished(newHolder, false)
        }
        return false
    }

    override fun endAnimation(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().cancel()

        if (pendingAdditions.remove(holder)) dispatchAddFinished(holder)
        if (pendingRemovals.remove(holder)) dispatchRemoveFinished(holder)

        resetView(holder)
        if (!endingAllAnimations && !isRunning()) dispatchAnimationsFinished()
    }

    override fun endAnimations() {
        endingAllAnimations = true
        pendingAdditions.toList().forEach(::endAnimation)
        pendingRemovals.toList().forEach(::endAnimation)
        runningAnimations.keys.toList().forEach(::endAnimation)
        endingAllAnimations = false
        dispatchAnimationsFinished()
    }

    override fun isRunning(): Boolean =
        pendingAdditions.isNotEmpty() ||
            pendingRemovals.isNotEmpty() ||
            runningAnimations.isNotEmpty()

    private fun resetView(holder: RecyclerView.ViewHolder) {
        holder.itemView.alpha = 1f
        holder.itemView.translationX = 0f
    }

    private companion object {
        const val ANIMATION_DURATION = 250L
        const val SLIDE_DISTANCE = 32f
    }
}
