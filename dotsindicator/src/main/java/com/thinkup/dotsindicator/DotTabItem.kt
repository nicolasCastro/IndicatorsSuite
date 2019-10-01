package com.thinkup.dotsindicator

import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.android.synthetic.main.dot_item.view.*
import android.util.TypedValue
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class DotTabItem(context: Context, attrs: AttributeSet? = null, private val config: DotConfig) :
    LinearLayout(context, attrs) {

    private var fromScaleX: Float = 0.0f
    private var toScaleX: Float = 0.0f

    init {
        inflate(context, R.layout.dot_item, this)
        changeBackground(false, config.rounded)

        fromScaleX = config.width.toFloat()
        toScaleX = config.selectedWidth.toFloat()

        val params = mainItemView.layoutParams as MarginLayoutParams
        params.height = config.height
        params.width = config.width
        params.setMargins(config.margin, config.margin, config.margin, config.margin)
        mainItemView.layoutParams = params
    }

    fun setItemSelected() {
        setItemSelected(true)
    }

    fun setItemDeselected() {
        setItemSelected(false)
    }

    private fun setItemSelected(selected: Boolean) {
        changeBackground(selected, config.rounded)
        scaleItem(selected)
    }

    private fun changeBackground(selected: Boolean, rounded: Boolean) {
        Handler().postDelayed({ mainItemView.background = getDrawable(selected, rounded) }, config.duration.toLong())
    }

    private fun getDrawable(selected: Boolean, rounded: Boolean): Drawable? {
        val value = TypedValue()
        resources.getValue(if (selected) config.selectedColor else config.unselectedColor, value, true) // will throw if resId doesn't exist

        if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
            val unwrappedDrawable = AppCompatResources.getDrawable(
                context,
                if (rounded) R.drawable.bg_item_round else R.drawable.bg_item_rect
            )?.mutate()
            unwrappedDrawable?.let {
                val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable)
                DrawableCompat.setTint(wrappedDrawable, getColor(selected))
                return unwrappedDrawable
            }
        } else {
            return AppCompatResources.getDrawable(
                context,
                value.resourceId
            )?.mutate()
        }
        return null
    }

    private fun getColor(selected: Boolean): Int {
        return if (selected) ContextCompat.getColor(context, config.selectedColor)
        else ContextCompat.getColor(context, config.unselectedColor)
    }

    private fun scaleItem(selected: Boolean) {
        val fromScaleX = if (selected) this.fromScaleX else this.toScaleX
        val toScaleX = if (selected) this.toScaleX else this.fromScaleX

        val anim = ValueAnimator.ofInt(fromScaleX.toInt(), toScaleX.toInt())
        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            scale(value)
        }
        anim.addListener(object : AnimatorListenerAdapter() {
        })
        anim.duration = config.duration.toLong()
        anim.start()
    }

    private fun scale(value: Int) {
        val layoutParams = mainItemView.layoutParams
        layoutParams.width = value
        mainItemView.layoutParams = layoutParams
    }
}
