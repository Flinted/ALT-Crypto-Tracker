package makes.flint.alt.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import makes.flint.alt.base.BaseFragment
import makes.flint.alt.ui.market.MarketFragment
import makes.flint.alt.ui.tracker.TrackerFragment

/**
 * MainViewPagerAdapter
 * Copyright © 2018  ChrisDidThis. All rights reserved.
 */
class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    // Properties

    private val fragments = SparseArray<BaseFragment>()

    // Overrides
    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as BaseFragment
        fragments.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        fragments.remove(position)
        super.destroyItem(container, position, `object`)
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MarketFragment()
            else -> TrackerFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    // Internal Functions

    internal fun getFragment(position: Int): Fragment? {
        return fragments.get(position)
    }

    internal fun showLoading() {
        var count = count
        while (count >= 0) {
            fragments[count]?.showLoading()
            count--
        }
    }
}
