package makes.flint.alt.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import makes.flint.alt.ui.market.MarketFragment
import makes.flint.alt.ui.tracker.TrackerFragment

/**
 * MainViewPagerAdapter
 * Copyright © 2018  Flint Makes. All rights reserved.
 */
class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = SparseArray<Fragment>()

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
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

    internal fun getFragment(position: Int): Fragment? {
        return fragments.get(position)
    }
}
