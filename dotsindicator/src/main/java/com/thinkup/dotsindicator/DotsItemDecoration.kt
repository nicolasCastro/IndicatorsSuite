package com.thinkup.dotsindicator

import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

internal abstract class DotsItemDecoration : RecyclerView.ItemDecoration() {
    private var current = 0
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val layoutManager = parent.layoutManager as LinearLayoutManager
        val activePosition = layoutManager.findFirstCompletelyVisibleItemPosition()
        if (activePosition == RecyclerView.NO_POSITION || activePosition == current) {
            return
        }
        current = activePosition
        select(activePosition)
    }

    abstract fun select(index: Int)
}