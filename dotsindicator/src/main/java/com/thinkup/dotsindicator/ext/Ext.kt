package com.thinkup.dotsindicator.ext

import android.graphics.Rect
import android.view.View
import com.thinkup.dotsindicator.DotTabItem

private fun View.visibleItem(item: DotTabItem): Boolean {
    val scrollBounds = Rect()
    getHitRect(scrollBounds)

    return item.getLocalVisibleRect(scrollBounds)
}