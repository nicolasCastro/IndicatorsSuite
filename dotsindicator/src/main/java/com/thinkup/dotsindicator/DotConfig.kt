package com.thinkup.dotsindicator

import android.util.AttributeSet
import android.content.Context
import androidx.core.content.ContextCompat

class DotConfig(context: Context, attrs: AttributeSet) {

    companion object {
        private const val ANIM_LARGE_DURATION = 250
    }

    init {
        with(context) {
            val attributes = obtainStyledAttributes(attrs, R.styleable.DotsView)
            with(attributes) {
                height = getDimensionPixelSize(
                    R.styleable.DotsView_dotHeight,
                    getDefaultDimen(context, R.dimen.dot_height)
                )
                width = getDimensionPixelSize(
                    R.styleable.DotsView_dotWidth,
                    getDefaultDimen(context, R.dimen.dot_width)
                )
                selectedWidth = getDimensionPixelSize(
                    R.styleable.DotsView_dotSelectedWidth,
                    getDefaultDimen(context, R.dimen.dot_width_select)
                )

                selectedColor = getColor(
                    R.styleable.DotsView_dotSelectedColor,
                    getDefaultColor(context, R.color.dot_selected)
                )
                unselectedColor = getColor(
                    R.styleable.DotsView_dotUnselectedColor,
                    getDefaultColor(context, R.color.dot_unselected)
                )

                margin = getDimensionPixelSize(
                    R.styleable.DotsView_dotMargin,
                    getDefaultDimen(context, R.dimen.dot_padding)
                )
                duration = getInt(R.styleable.DotsView_animationDuration, ANIM_LARGE_DURATION)
            }
        }
    }

    private fun getDefaultDimen(context: Context, id: Int): Int = context.resources.getDimensionPixelSize(id)
    private fun getDefaultColor(context: Context, id: Int): Int = ContextCompat.getColor(context, id)

    var unselectedColor: Int
        internal set
    var selectedColor: Int
        internal set
    var width: Int
        internal set
    var selectedWidth: Int
        internal set
    var height: Int
        internal set
    var margin: Int
        internal set
    var duration: Int
        internal set
}