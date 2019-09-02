package com.thinkup.dotsindicator

import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.dot_item.view.*

class DotTabItem(context: Context, attrs: AttributeSet? = null, private val config: DotConfig) :
    LinearLayout(context, attrs) {

    private var fromScaleX: Float = 0.0f
    private var toScaleX: Float = 0.0f

    init {
        inflate(context, R.layout.dot_item, this)
        changeBackground(false)

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
        changeBackground(selected)
        scaleItem(selected)
    }

    private fun changeBackground(selected: Boolean) {
        val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.bg_item_selected)?.mutate()
        unwrappedDrawable?.let {
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable)
            DrawableCompat.setTint(wrappedDrawable, getColor(selected))
        }

        Handler().postDelayed({ mainItemView.background = unwrappedDrawable }, config.duration.toLong())
    }

    private fun getColor(selected: Boolean): Int {
        return if (selected) config.selectedColor
        else config.unselectedColor
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

    private fun calculateScrollXForTab(selectedChild: View): Int {
        val selectedWidth = selectedChild.width

        // base scroll amount: places center of tab in center of parent
        val scrollBase = selectedChild.left + selectedWidth / 2 - width / 2
        // offset amount: fraction of the distance between centers of tabs

        return if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_LTR)
            scrollBase
        else
            scrollBase
    }

    private fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }
}
