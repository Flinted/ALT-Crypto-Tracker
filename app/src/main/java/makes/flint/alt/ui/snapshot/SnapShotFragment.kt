package makes.flint.alt.ui.snapshot

import android.os.Bundle
import makes.flint.alt.R
import makes.flint.alt.base.BaseActivity

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
}