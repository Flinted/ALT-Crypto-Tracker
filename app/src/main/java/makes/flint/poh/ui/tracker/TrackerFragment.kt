package makes.flint.poh.ui.tracker

import android.os.Bundle
import makes.flint.poh.base.BaseFragment

/**
 * TrackerFragment
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class TrackerFragment: BaseFragment(), TrackerContractView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("TRACKER FRAGMENT")
    }
}