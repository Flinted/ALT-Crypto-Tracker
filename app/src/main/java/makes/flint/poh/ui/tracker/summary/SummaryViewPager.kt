package makes.flint.poh.ui.tracker.summary

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

/**
 * SummaryViewPager
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SummaryViewPager(context: Context, attributeSet: AttributeSet) : ViewPager(context, attributeSet) {

    override fun canScrollHorizontally(direction: Int): Boolean {
        return false
    }

    override fun canScrollVertically(direction: Int): Boolean {
        return false
    }
}