package ru.otus.cryptosample.coins.feature.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.animation.AnticipateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class CustomItemAnimator : DefaultItemAnimator() {

    private val customAnimators = mutableMapOf<RecyclerView.ViewHolder, Animator>()

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.alpha = 0f
        holder.itemView.scaleX = 0f
        holder.itemView.scaleY = 0f
        holder.itemView.translationY = 100f
        return super.animateAdd(holder)
    }

    override fun onAddStarting(item: RecyclerView.ViewHolder) {
        super.onAddStarting(item)
        
        val pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f)
        val pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f)
        val pvhTranslationY = PropertyValuesHolder.ofFloat("translationY", 0f)
        
        val anim = ObjectAnimator.ofPropertyValuesHolder(item.itemView, pvhScaleX, pvhScaleY, pvhTranslationY)
        anim.duration = addDuration
        anim.interpolator = OvershootInterpolator(1.5f)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                customAnimators.remove(item)
            }
        })
        customAnimators[item] = anim
        anim.start()
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        return super.animateRemove(holder)
    }

    override fun onRemoveStarting(item: RecyclerView.ViewHolder) {
        super.onRemoveStarting(item)
        
        val pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 0f)
        val pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 0f)
        val pvhTranslationY = PropertyValuesHolder.ofFloat("translationY", 100f)
        
        val anim = ObjectAnimator.ofPropertyValuesHolder(item.itemView, pvhScaleX, pvhScaleY, pvhTranslationY)
        anim.duration = removeDuration
        anim.interpolator = AnticipateInterpolator(1.5f)
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                customAnimators.remove(item)
            }
        })
        customAnimators[item] = anim
        anim.start()
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        super.endAnimation(item)
        customAnimators[item]?.cancel()
        customAnimators.remove(item)
        resetState(item)
    }

    override fun endAnimations() {
        super.endAnimations()
        customAnimators.values.toList().forEach { it.cancel() }
        customAnimators.clear()
    }

    override fun onAddFinished(item: RecyclerView.ViewHolder) {
        super.onAddFinished(item)
        resetState(item)
    }

    override fun onRemoveFinished(item: RecyclerView.ViewHolder) {
        super.onRemoveFinished(item)
        resetState(item)
    }

    private fun resetState(item: RecyclerView.ViewHolder) {
        item.itemView.alpha = 1f
        item.itemView.scaleX = 1f
        item.itemView.scaleY = 1f
        item.itemView.translationY = 0f
    }
}
