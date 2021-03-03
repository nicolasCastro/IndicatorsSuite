package com.thinkup.stepsindicator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.*
import androidx.recyclerview.widget.RecyclerView
import com.thinkup.stepsindicator.ext.STEP_FACTOR
import kotlinx.android.synthetic.main.steps_control.view.*

//TODO: individual ste color, individual icons, text size & font
class StepsView(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet) {

    private var config: StepsConfig
    private var tabItems = mutableListOf<StepItem>()
    private var currentSelectedIndex: Int = 0
    private var callback: Callback? = null

    init {
        View.inflate(context, R.layout.steps_control, this)
        config = StepsConfig(context, attributeSet)

        with(context) {
            val attributes = obtainStyledAttributes(attributeSet, R.styleable.StepsView)
            with(attributes) {
                val count = getInteger(R.styleable.StepsView_stepsCount, 0)
                val current = getInteger(R.styleable.StepsView_currentStep, 0)

                if (count > 0) loadItems(count, current)
            }
            attributes.recycle()
        }
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    private fun setConfig(stepsCount: Int, currentStep: Int, config: StepsConfig) {
        this.config = config
        loadItems(stepsCount, currentStep)
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    fun loadItems(stepsCount: Int, currentStep: Int) {
        tkupContainer.layoutParams.height = if (config.selectedSize > config.size) config.selectedSize else config.size
        tkupContainer.weightSum = (stepsCount.toFloat() - 1) * STEP_FACTOR
        for (i in 0 until stepsCount) {
            addStepItem(i, i == 0, i == stepsCount - 1)
        }
        currentSelectedIndex = if (currentStep < stepsCount) currentStep else stepsCount - 1
        setSelectedSteps()
    }

    fun next() {
        selectIndex(currentSelectedIndex + 1)
    }

    fun previous() {
        selectIndex(currentSelectedIndex - 1)
    }

    fun attach(view: RecyclerView) {
        val items = view.adapter?.itemCount ?: 0
        if (items > 0) loadItems(view.adapter?.itemCount ?: 0, 0)
        view.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                val count = view.adapter?.itemCount ?: 0
                if (count > 0) loadItems(items, 0)
            }
        })
        view.addItemDecoration(object : StepsItemDecoration() {
            override fun select(index: Int) {
                selectIndex(index)
            }
        })
    }

    private fun setSelectedSteps() {
        for (i in 0..currentSelectedIndex) {
            selectIndex(i)
        }
    }

    private fun setSelectedSteps(index: Int) {
        val previous = currentSelectedIndex
        currentSelectedIndex = index
        if (previous < currentSelectedIndex)
            for (i in previous..currentSelectedIndex) {
                selectIndex(i)
            }
        else
            for (i in previous downTo currentSelectedIndex) {
                selectIndex(i)
            }
    }

    private fun addStepItem(index: Int, isFirst: Boolean, isLast: Boolean) {
        val stepTabItem = StepItem(context, config = config)
        tabItems.add(stepTabItem)
        tkupContainer.addView(stepTabItem)
        stepTabItem.init(index, isFirst, isLast)
        if (config.isTouchable) stepTabItem.setOnClickListener {
            val previous = currentSelectedIndex
            setSelectedSteps(index)
            callback?.onStepChanged(previous, it)
        }
    }

    private fun selectIndex(index: Int) {
        if (checkIndex(index)) {
            val previous = currentSelectedIndex
            tabItems[currentSelectedIndex].setItemDeselected(currentSelectedIndex < index)
            if (currentSelectedIndex > index) tabItems[currentSelectedIndex].loadReverseProgress()
            else tabItems[currentSelectedIndex].loadProgress()
            currentSelectedIndex = index
            tabItems[currentSelectedIndex].setItemSelected(currentSelectedIndex < previous)
        }
    }

    private fun checkIndex(index: Int) = index >= 0 && index < tabItems.size

    interface Callback {
        fun onStepChanged(oldIndex: Int, newIndex: Int)
    }

    class Builder(private val context: Context) {
        private val config: StepsConfig = StepsConfig(context)
        private var count = 0
        private var current = 0
        private var callback: Callback? = null

        fun setUncompletedColor(@AnyRes uncompletedColor: Int): Builder {
            config.uncompletedColor = uncompletedColor
            return this
        }

        fun setCompletedColor(@AnyRes completedColor: Int): Builder {
            config.completedColor = completedColor
            return this
        }

        fun setSize(@DimenRes size: Int): Builder {
            config.size = config.getDefaultDimen(context, size)
            return this
        }

        fun setSelectedSize(@DimenRes size: Int): Builder {
            config.selectedSize = config.getDefaultDimen(context, size)
            return this
        }

        fun setLineUncompletedColor(@ColorRes color: Int): Builder {
            config.lineUncompletedColor = color
            return this
        }

        fun setLineCompletedColor(@ColorRes color: Int): Builder {
            config.lineCompletedColor = color
            return this
        }

        fun setLineThickness(@DimenRes size: Int): Builder {
            config.lineThickness = config.getDefaultDimen(context, size)
            return this
        }

        fun setTextUncompletedColor(@ColorRes color: Int): Builder {
            config.textUncompletedColor = color
            return this
        }

        fun setTextCompletedColor(@ColorRes color: Int): Builder {
            config.textCompletedColor = color
            return this
        }

        fun setIconCompleted(@DrawableRes icon: Int): Builder {
            config.iconCompleted = icon
            return this
        }

        fun setDuration(duration: Int): Builder {
            config.duration = duration
            return this
        }

        fun setBorder(borders: Boolean): Builder {
            config.border = borders
            return this
        }

        fun setShownCompetedIcon(show: Boolean): Builder {
            config.shownCompetedIcon = show
            return this
        }

        fun setIsTouchable(isTouchable: Boolean): Builder {
            config.isTouchable = isTouchable
            return this
        }

        fun setStepsCount(count: Int): Builder {
            this.count = count
            return this
        }

        fun setCurrentIndex(index: Int): Builder {
            this.current = index
            return this
        }

        fun setCallback(callback: Callback): Builder {
            this.callback = callback
            return this
        }

        fun build(): StepsView {
            val view = StepsView(context)
            view.setConfig(count, current, config)
            callback?.let { view.setCallback(it) }
            return view
        }
    }
}