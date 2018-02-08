package makes.flint.poh.ui.tracker.summary

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import makes.flint.poh.data.Summary
import makes.flint.poh.ui.interfaces.SummaryUpdatable

/**
 * SummaryViewPagerAdapter
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SummaryViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragments: MutableList<SummaryUpdatable> = mutableListOf()

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        fragments.add(position, fragment as SummaryUpdatable)
        return fragment
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        fragments.removeAt(position)
        super.destroyItem(container, position, `object`)
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SummaryFragment()
            else -> SummaryChartFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    fun setNewSummary(summary: Summary) {
        fragments.forEach {
            it.updateForSummary(summary)
        }
    }
}