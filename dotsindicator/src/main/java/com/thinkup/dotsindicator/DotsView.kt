package com.thinkup.dotsindicator

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView
import androidx.annotation.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dots_scroll_control.view.*
import java.util.*

class DotsView(context: Context, attrs: AttributeSet? = null) :
    HorizontalScrollView(context, attrs, 0) {

    companion object {
        const val DEFAULT_DELAY = 500
        const val LOADER_INFINITE = -1
    }

    private var padding: Int = 0
    private var gradient = false
    private var tabItems = mutableListOf<DotTabItem>()
    private var currentSelectedIndex: Int = 0
    private var config: DotConfig
    private var callback: Callback? = null

    init {
        inflate(context, R.layout.dots_scroll_control, this)
        config = DotConfig(context, attrs)

        with(context) {
            val attributes = obtainStyledAttributes(attrs, R.styleable.DotsView)
            with(attributes) {
                val size = getInteger(R.styleable.DotsView_itemsCount, 0)
                val current = getInteger(R.styleable.DotsView_currentIndex, 0)
                if (size > 0) loadItems(size, current)
                // TODO: add to the Builder
                val loader = getBoolean(R.styleable.DotsView_loader, false)
                val delay = getInteger(R.styleable.DotsView_loaderDelay, DEFAULT_DELAY.toInt())
                val repeat = getInteger(R.styleable.DotsView_loaderRepeatCount, LOADER_INFINITE)
                gradient = getBoolean(R.styleable.DotsView_gradient, false)
                if (loader) loader(delay.toLong(), repeat, current)
            }
            attributes.recycle()
        }

        padding = resources.getDimensionPixelSize(R.dimen.dot_standard_size)
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    private fun setConfig(
        stepsCount: Int,
        currentStep: Int,
        gradient: Boolean,
        loader: Boolean,
        delay: Int,
        repeat: Int,
        config: DotConfig
    ) {
        this.config = config
        loadItems(stepsCount, currentStep)
        this.gradient = gradient
        if (loader) loader(delay.toLong(), repeat, currentStep)
    }

    fun loadItems(size: Int, currentIndex: Int) {
        restoreView(currentIndex)
        for (i in 0 until size) {
            addDotItem()
        }
        visibility = View.VISIBLE
        scrollView.visibility = View.VISIBLE
        tabItems[currentSelectedIndex].setItemSelected()
        setSelectedSteps()
        applyGradient()
    }

    private fun setSelectedSteps() {
        if (config.steps) {
            for (i in 0..currentSelectedIndex) {
                tabItems[i].setItemSelected()
            }
        }
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
        view.addItemDecoration(object : DotsItemDecoration() {
            override fun select(index: Int) {
                selectIndex(index)
            }
        })
    }

    fun loader(delay: Long = DEFAULT_DELAY.toLong(), repeatCount: Int = LOADER_INFINITE, currentIndex: Int = 0) {
        selectIndex(currentIndex)
        load(delay, repeatCount, if (repeatCount == LOADER_INFINITE) LOADER_INFINITE else 0)
    }

    private fun load(delay: Long = 500, repeatCount: Int = LOADER_INFINITE, actualRepeat: Int = 0) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (currentSelectedIndex < tabItems.size - 1) next() else selectIndex(0)
            val count =
                if (repeatCount != LOADER_INFINITE && currentSelectedIndex == 0) actualRepeat + 1 else actualRepeat
            if (count == LOADER_INFINITE || count < repeatCount) load(delay, repeatCount, count)
        }, delay)
    }

    fun next() {
        selectIndex(currentSelectedIndex + 1)
    }

    fun previous() {
        selectIndex(currentSelectedIndex - 1)
    }

    fun selectIndex(index: Int) {
        if (checkIndex(index)) {
            val previous = currentSelectedIndex
            tabItems[currentSelectedIndex].setItemDeselected()
            currentSelectedIndex = index
            tabItems[currentSelectedIndex].setItemSelected()
            setSelectedSteps()
            callback?.onIndexChange(previous, currentSelectedIndex)
            applyGradient()
        }
    }

    private fun applyGradient() {
        if (gradient) {
            if (checkIndex(currentSelectedIndex + 1)) tabItems[currentSelectedIndex + 1].updateAlpha(config.gradientNearNextPercentage)
            if (checkIndex(currentSelectedIndex - 1)) tabItems[currentSelectedIndex - 1].updateAlpha(config.gradientNearPrePercentage)
            tabItems.forEachIndexed { i, dotTabItem ->
                if (i !in currentSelectedIndex - 1..currentSelectedIndex + 1) dotTabItem.updateAlpha(config.gradientFarPercentage)
            }
        }
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    private fun checkIndex(index: Int) = index >= 0 && index < tabItems.size

    private fun restoreView(currentIndex: Int) {
        this.tabItems = ArrayList()
        this.currentSelectedIndex = currentIndex
        container.removeAllViews()
    }

    private fun addDotItem() {
        val dotTabItem = DotTabItem(context, config = config)
        tabItems.add(dotTabItem)
        container.addView(dotTabItem)
    }

    interface Callback {
        fun onIndexChange(previous: Int, current: Int)
    }

    class Builder(private val context: Context) {
        private val config: DotConfig = DotConfig(context)
        private var count = 0
        private var current = 0
        private var gradient = false
        private var loader = false
        private var delay = DEFAULT_DELAY.toInt()
        private var repeat = LOADER_INFINITE
        private var callback: Callback? = null

        fun setUnselectedResource(@AnyRes unselected: Int): Builder {
            config.unselectedColor = unselected
            return this
        }

        fun setSelectedResource(@AnyRes selected: Int): Builder {
            config.selectedColor = selected
            return this
        }

        fun setWidth(@DimenRes size: Int): Builder {
            config.width = config.getDefaultDimen(context, size)
            return this
        }

        fun setSelectedWidth(@DimenRes size: Int): Builder {
            config.selectedWidth = config.getDefaultDimen(context, size)
            return this
        }

        fun setHeight(@DimenRes size: Int): Builder {
            config.height = config.getDefaultDimen(context, size)
            return this
        }

        fun setMargin(@DimenRes size: Int): Builder {
            config.margin = config.getDefaultDimen(context, size)
            return this
        }

        fun setDuration(duration: Int): Builder {
            config.duration = duration
            return this
        }

        fun setRounded(rounded: Boolean): Builder {
            config.rounded = rounded
            return this
        }

        fun setSteps(steps: Boolean): Builder {
            config.steps = steps
            return this
        }

        fun setBorder(borders: Boolean): Builder {
            config.border = borders
            return this
        }

        fun setGradient(gradient: Boolean): Builder {
            this.gradient = gradient
            return this
        }

        fun setGradientNearNextPercentage(gradientNearNextPercentage: Int): Builder {
            config.gradientNearNextPercentage = gradientNearNextPercentage
            return this
        }

        fun setGradientNearPrePercentage(gradientNearPrePercentage: Int): Builder {
            config.gradientNearPrePercentage = gradientNearPrePercentage
            return this
        }

        fun setGradientFarPercentage(gradientFarPercentage: Int): Builder {
            config.gradientFarPercentage = gradientFarPercentage
            return this
        }

        fun setGradientSelectedPercentage(gradientSelectedPercentage: Int): Builder {
            config.gradientSelectedPercentage = gradientSelectedPercentage
            return this
        }

        fun setLoader(loader: Boolean): Builder {
            this.loader = loader
            return this
        }

        fun setLoaderDelay(delay: Int): Builder {
            this.delay = delay
            return this
        }

        fun setRepeat(repeat: Int): Builder {
            this.repeat = repeat
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

        fun build(): DotsView {
            val view = DotsView(context)
            view.setConfig(count, current, gradient, loader, delay, repeat, config)
            callback?.let { view.setCallback(it) }
            return view
        }
    }
}
