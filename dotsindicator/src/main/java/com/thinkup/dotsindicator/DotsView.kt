package com.thinkup.dotsindicator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dots_scroll_control.view.*
import java.util.*

class DotsView(context: Context, attrs: AttributeSet) :
    HorizontalScrollView(context, attrs, 0) {

    private var padding: Int = 0

    private var tabItems = mutableListOf<DotTabItem>()
    private var currentSelectedIndex: Int = 0
    private val config: DotConfig
    private var callback: DotsCallback? = null

    init {
        inflate(context, R.layout.dots_scroll_control, this)
        config = DotConfig(context, attrs)
        padding = resources.getDimensionPixelSize(R.dimen.dot_standard_size)
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
    }

    fun loadItems(size: Int, currentIndex: Int) {
        restoreView(currentIndex)
        for (i in 0 until size) {
            addDotItem()
        }
        visibility = View.VISIBLE
        scrollView.visibility = View.VISIBLE
        tabItems[currentSelectedIndex].setItemSelected()
    }

    fun attach(view: RecyclerView) {
        loadItems(view.adapter?.itemCount ?: 0, 0)
        view.adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                loadItems(view.adapter?.itemCount ?: 0, 0)
            }
        })
        view.addItemDecoration(object : DotsItemDecoration() {
            override fun select(index: Int) {
                selectIndex(index)
            }
        })
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
            callback?.onIndexChange(previous, currentSelectedIndex)
        }
    }

    fun setCallback(callback: DotsCallback) {
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
}
