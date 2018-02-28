package makes.flint.alt.ui.snapshot

import android.os.Bundle
import makes.flint.alt.R
import makes.flint.alt.base.BaseActivity
import makes.flint.alt.data.snapshot.SnapShot

/**
 * SnapShotFragment
 * Copyright © 2018 FlintMakes. All rights reserved.
 */
class SnapShotFragment : BaseActivity(), SnapShotContractView {

    private lateinit var views: SnapShotViewHolder
    private lateinit var snapShotPresenter: SnapShotContractPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_snapshot)
        this.snapShotPresenter = getPresenterComponent().provideSnapShotPresenter()
        this.snapShotPresenter.attachView(this)
        this.attachPresenter(snapShotPresenter)
        this.views = SnapShotViewHolder(this)
        this.snapShotPresenter.initialise()
    }

    override fun displaySnapShots(snapShots: List<SnapShot>) {
        val currentSnapShot = snapShots.first()
        val valueBTCString = getString(R.string.title_valueBTC, currentSnapShot.valueBTCFormatted())
        val valueUSDString = getString(R.string.title_valueUSD, currentSnapShot.valueUSDFormatted())
        views.valueUSD.text = valueUSDString
        views.valueBTC.text = valueBTCString
    }
}
