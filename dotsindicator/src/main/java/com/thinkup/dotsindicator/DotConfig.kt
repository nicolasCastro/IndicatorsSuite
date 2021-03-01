package com.thinkup.dotsindicator

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.RestrictTo

class DotConfig(context: Context, attrs: AttributeSet? = null) {

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

                selectedColor = getResourceId(
                    R.styleable.DotsView_dotSelectedColor,
                    R.color.dot_selected
                )
                unselectedColor = getResourceId(
                    R.styleable.DotsView_dotUnselectedColor,
                    R.color.dot_unselected
                )

                margin = getDimensionPixelSize(
                    R.styleable.DotsView_dotMargin,
                    getDefaultDimen(context, R.dimen.dot_padding)
                )
                duration = getInt(R.styleable.DotsView_animationDuration, ANIM_LARGE_DURATION)
                rounded = getBoolean(R.styleable.DotsView_rounded, true)
                steps = getBoolean(R.styleable.DotsView_steps, false)
                border = getBoolean(R.styleable.DotsView_borders, true)
                gradientSelectedPercentage = getInt(R.styleable.DotsView_gradientSelectedPercentage, 100)
                gradientNearNextPercentage = getInt(R.styleable.DotsView_gradientNearNextPercentage, 60)
                gradientNearPrePercentage = getInt(R.styleable.DotsView_gradientNearPrePercentage, 60)
                gradientFarPercentage = getInt(R.styleable.DotsView_gradientFarPercentage, 30)
            }
        }
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    fun getDefaultDimen(context: Context, id: Int): Int = context.resources.getDimensionPixelSize(id)

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
    var rounded: Boolean
        internal set
    var steps: Boolean
        internal set
    var border: Boolean
        internal set
    var gradientNearNextPercentage: Int
        internal set
    var gradientNearPrePercentage: Int
        internal set
    var gradientFarPercentage: Int
        internal set
    var gradientSelectedPercentage: Int
        internal set
}