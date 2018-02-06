package makes.flint.poh.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.poh.R
import makes.flint.poh.base.BaseFragment

/**
 * SettingsFragment
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SettingsFragment : BaseFragment(), SettingsContractView {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_settings, container, false)
        return view
    }
}