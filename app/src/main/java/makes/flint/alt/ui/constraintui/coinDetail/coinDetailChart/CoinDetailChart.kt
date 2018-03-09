package makes.flint.alt.ui.constraintui.coinDetail.coinDetailChart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import makes.flint.alt.R
import makes.flint.alt.base.BaseFragment

/**
 * CoinDetailChart
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class CoinDetailChart: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_coin_detail_chart, container, false)
        return view
    }
}
