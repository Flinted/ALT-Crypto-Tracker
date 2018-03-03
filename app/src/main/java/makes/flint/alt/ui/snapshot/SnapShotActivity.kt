package makes.flint.alt.ui.snapshot

import android.os.Bundle
import makes.flint.alt.R
import makes.flint.alt.base.BaseActivity
import makes.flint.alt.data.snapshot.SnapShot

/**
 * SnapShotActivity
 * Copyright © 2018 FlintMakes. All rights reserved.
 */
class SnapShotActivity : BaseActivity(), SnapShotContractView {

    // Properties

    private lateinit var views: SnapShotViewHolder
    private lateinit var snapShotPresenter: SnapShotContractPresenter

    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snapshot)
        this.snapShotPresenter = getPresenterComponent().provideSnapShotPresenter()
        this.snapShotPresenter.attachView(this)
        this.attachPresenter(snapShotPresenter)
        this.views = SnapShotViewHolder(this)
        this.snapShotPresenter.initialise()
    }

    // Overrides

    override fun displaySnapShots(snapShots: List<SnapShot>) {
        val currentSnapShot = snapShots.first()
        val valueBTCString = getString(R.string.title_valueBTC, currentSnapShot.valueBTCFormatted())
        val valueUSDString = getString(R.string.title_valueUSD, currentSnapShot.valueUSDFormatted())
        views.valueUSD.text = valueUSDString
        views.valueBTC.text = valueBTCString
    }
}
