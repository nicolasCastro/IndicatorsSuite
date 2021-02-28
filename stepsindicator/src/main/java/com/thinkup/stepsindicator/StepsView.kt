package com.thinkup.stepsindicator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.thinkup.stepsindicator.ext.STEP_FACTOR
import kotlinx.android.synthetic.main.steps_control.view.*

//TODO: steps count, steps icons, steps type (numeric, icon), step label, individual ste color
// attach to a recycler
class StepsView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    private val config: StepsConfig
    private var tabItems = mutableListOf<StepItem>()
    private var currentSelectedIndex: Int = 0

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

    fun loadItems(stepsCount: Int, currentStep: Int) {
        container.weightSum = (stepsCount.toFloat() - 1) * STEP_FACTOR
        for (i in 0 until stepsCount) {
            addStepItem(i, i == 0, i == stepsCount - 1)
        }
        currentSelectedIndex = currentStep
        setSelectedSteps()
    }

    private fun setSelectedSteps() {
        for (i in 0..currentSelectedIndex) {
            tabItems[i].setItemSelected()
        }
    }

    private fun addStepItem(index: Int, isFirst: Boolean, isLast: Boolean) {
        val stepTabItem = StepItem(context, config = config)
        tabItems.add(stepTabItem)
        container.addView(stepTabItem)
        stepTabItem.init(index, isFirst, isLast)
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

    fun next() {
        selectIndex(currentSelectedIndex + 1)
    }

    fun previous() {
        selectIndex(currentSelectedIndex - 1)
    }
}