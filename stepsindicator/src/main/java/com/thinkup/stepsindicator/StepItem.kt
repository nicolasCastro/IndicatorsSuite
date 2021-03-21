package com.thinkup.stepsindicator

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.thinkup.stepsindicator.ext.*
import kotlinx.android.synthetic.main.steps_item.view.*

class StepItem(context: Context, attrs: AttributeSet? = null, private val config: StepsConfig) : LinearLayout(context, attrs) {

    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, StepsConfig(context, attrs!!))

    enum class Status { NONE, SELECTED, COMPLETED }

    private var fromScaleX: Float = 1.0f
    private var toScaleX: Float = 1.0f
    private var lastfactor: Int = MIN_STEP_FACTOR
    private var index: Int = 0
    private var status: Status = StepItem.Status.NONE

    fun init(index: Int, isFirst: Boolean, isLast: Boolean) {
        tkupStepsControlText.text = "${index + 1}"
        layoutParams.toLinear().weight = STEP_FACTOR.toFloat()
        tkupStepsItemContainer.layoutParams.toLinear().weight = STEP_FACTOR.toFloat()
        tkupStepsProgressContainer.weightSum = STEP_FACTOR.toFloat()
        when {
            isFirst -> tkupStepsProgressContainer.setMargins(left = config.size / 2)
            isLast -> {
                tkupStepsProgressContainer.setMargins(right = config.selectedSize - (config.size / 2))
                tkupStepsProgressContainer.layoutParams.width = config.size / 2
            }
        }
        fromScaleX = config.size.toFloat()
        toScaleX = config.selectedSize.toFloat()
        lastfactor = if (isLast) STEP_FACTOR else MIN_STEP_FACTOR
        this.index = index
        setBackground(false, config.border)
        setSizes()
        tkupStepsProgressContainer.backgroundTintList = ColorStateList.valueOf(getLineColor(false))
        tkupStepsControlProgress.backgroundTintList = ColorStateList.valueOf(getLineColor(true))
        tkupStepsControlIcon.imageTintList = ColorStateList.valueOf(getColor(config.textCompletedColor))
        tkupStepsControlText.setTextColor(makeSelectorTextColor())
        tkupStepsControlText.isEnabled = false
        tkupStepsControlIcon.isVisible = false
        tkupStepsControlText.isVisible = config.shownUncompletedResource
        tkupStepsControlIcon.setImageResource(config.iconCompleted)
        tkupStepsControlIcon.setPadding(config.iconPadding)
    }

    fun setOnClickListener(action: (Int) -> Unit) {
        tkupStepsControlItemView.setOnClickListener { action(index) }
    }

    init {
        inflate(context, R.layout.steps_item, this)
    }

    fun setItemSelected(animated: Boolean = false) {
        setInternalItemSelected(Status.SELECTED)
        if (!animated) tkupStepsControlProgress.animateProgress(lastfactor, duration = config.duration.toLong())
        else tkupStepsControlProgress.animateProgress(STEP_FACTOR, duration = config.duration.toLong(), reverse = true)
    }

    fun loadProgress() {
        tkupStepsControlProgress.animateProgress(STEP_FACTOR, duration = config.duration.toLong())
    }

    fun loadReverseProgress() {
        tkupStepsControlProgress.animateProgress(STEP_FACTOR, 0, duration = 0, reverse = true)
    }

    fun setItemDeselected(completed: Boolean) {
        setInternalItemSelected(if (completed) Status.COMPLETED else Status.NONE)
    }

    private fun setInternalItemSelected(status: Status) {
        val currentStatus = this.status
        this.status = status
        val from = currentStatus == Status.SELECTED && status == Status.COMPLETED
        val xfrom = currentStatus == Status.COMPLETED
        val completed = currentStatus == Status.COMPLETED && status == Status.COMPLETED
        val to = currentStatus == Status.SELECTED && status == Status.NONE
        tkupStepsControlText.isEnabled = status == Status.SELECTED || status == Status.COMPLETED
        changeBackground(status == Status.SELECTED || status == Status.COMPLETED, config.border)
        if (!(config.keepCompletedSize && (from || completed || (!to && currentStatus == Status.SELECTED) || (!to && xfrom))))
            scaleItem(tkupStepsControlItemView, status == Status.SELECTED)
        if (config.shownCompletedIcon) scaleItem(tkupStepsControlIcon, status != Status.COMPLETED) {
            tkupStepsControlIcon.isVisible = status == Status.COMPLETED
        }
        if (config.shownCompletedIcon) scaleItem(tkupStepsControlText, status != Status.COMPLETED) {
            tkupStepsControlText.isVisible = (status != Status.COMPLETED && config.shownUncompletedResource) || status == Status.SELECTED
        }
    }

    private fun changeBackground(selected: Boolean, border: Boolean) {
        Handler(Looper.getMainLooper()).postDelayed({ setBackground(selected, border) }, config.duration.toLong())
    }

    private fun setBackground(selected: Boolean, border: Boolean) {
        tkupStepsControlItemView.background = getDrawable(selected, border)
    }

    private fun getDrawable(selected: Boolean, border: Boolean): Drawable? {
        val value = TypedValue()
        resources.getValue(if (selected) config.completedColor else config.uncompletedColor, value, true) // will throw if resId doesn't exist

        if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
            val unwrappedDrawable = AppCompatResources.getDrawable(
                context,
                R.drawable.step_rounded_item
            )?.mutate()
            unwrappedDrawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable)
                if (!border) DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.SRC_ATOP)
                DrawableCompat.setTint(wrappedDrawable, getColor(selected))

                return unwrappedDrawable
            }
        } else {
            return AppCompatResources.getDrawable(context, value.resourceId)?.mutate()
        }
        return null
    }

    private fun getColor(selected: Boolean): Int {
        return if (selected) ContextCompat.getColor(context, config.completedColor)
        else ContextCompat.getColor(context, config.uncompletedColor)
    }

    private fun getLineColor(progress: Boolean): Int {
        return if (progress) ContextCompat.getColor(context, config.lineCompletedColor)
        else ContextCompat.getColor(context, config.lineUncompletedColor)
    }

    private fun getColor(id: Int) = ContextCompat.getColor(context, id)

    private fun scaleItem(target: View, selected: Boolean, listener: (() -> Unit)? = null) {
        val fromScaleX = if (selected) this.fromScaleX else this.toScaleX
        val toScaleX = if (selected) this.toScaleX else this.fromScaleX

        val anim = ValueAnimator.ofInt(fromScaleX.toInt(), toScaleX.toInt())
        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            scale(target, value)
        }
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                listener?.invoke()
            }
        })
        anim.duration = config.duration.toLong()
        anim.start()
    }

    private fun setSizes() {
        scale(tkupStepsControlItemView, config.size)
        scale(tkupStepsProgressContainer, config.lineThickness, scaleWidth = false)
    }

    private fun scale(target: View, value: Int, scaleHeight: Boolean = true, scaleWidth: Boolean = true) {
        val layoutParams = target.layoutParams
        if (scaleWidth) layoutParams.width = value
        if (scaleHeight) layoutParams.height = value
        target.layoutParams = layoutParams
    }

    private fun makeSelectorTextColor(): ColorStateList {
        val states = arrayOf(intArrayOf(android.R.attr.state_enabled), intArrayOf(-android.R.attr.state_enabled))
        val colors = intArrayOf(getColor(config.textCompletedColor), getColor(config.textUncompletedColor))
        return ColorStateList(states, colors)
    }
}
