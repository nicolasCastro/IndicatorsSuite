package com.thinkup.dotsindicator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView
import kotlinx.android.synthetic.main.dots_scroll_control.view.*
import java.util.*

class DotsView(context: Context, attrs: AttributeSet) :
    HorizontalScrollView(context, attrs, 0) {

    private var padding: Int = 0

    private var tabItems = mutableListOf<DotTabItem>()
    private var currentSelectedIndex: Int = 0
    private var tabClick: OnTabClick? = null
    private val config: DotConfig

    init {
        inflate(context, R.layout.dots_scroll_control, this)
        config = DotConfig(context, attrs)
        padding = resources.getDimensionPixelSize(R.dimen.dot_standard_size)
        isVerticalScrollBarEnabled = false
        isHorizontalScrollBarEnabled = false
    }

    fun setTabClickListener(tabClick: OnTabClick) {
        this.tabClick = tabClick
    }

    fun loadItems(size: Int, currentIndex: Int) {
        restoreView(currentIndex)
        for (i in 0 until size) {
            addDotItem(i)
        }
        visibility = View.VISIBLE
        scrollView.visibility = View.VISIBLE
        tabItems[currentSelectedIndex].setItemSelected()
    }

    fun next() {
        if (currentSelectedIndex < tabItems.size - 1) {
            tabItems[currentSelectedIndex].setItemDeselected()
            currentSelectedIndex++
            tabItems[currentSelectedIndex].setItemSelected()
        }
    }

    fun previous() {
        if (currentSelectedIndex > 0) {
            tabItems[currentSelectedIndex].setItemDeselected()
            currentSelectedIndex--
            tabItems[currentSelectedIndex].setItemSelected()
        }
    }

    fun selectIndex(index: Int) {
        if (tabItems.size > index) {
            tabItems[currentSelectedIndex].setItemDeselected()
            currentSelectedIndex = index
            tabItems[currentSelectedIndex].setItemSelected()
        }
    }

    private fun restoreView(currentIndex: Int) {
        this.tabItems = ArrayList()
        this.currentSelectedIndex = currentIndex
        container.removeAllViews()
    }

    private fun addDotItem(index: Int) {
        val dotTabItem = DotTabItem(context, config = config)
        addListeners(dotTabItem, index)
        tabItems.add(dotTabItem)
        container.addView(dotTabItem)
    }

    private fun addListeners(item: DotTabItem, index: Int) {
        item.setOnClickListener {
            tabClick?.selectTab(index)
        }
    }

    interface OnTabClick {
        fun selectTab(index: Int)
    }
}
