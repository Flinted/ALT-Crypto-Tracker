package makes.flint.alt.ui.snapshot

import makes.flint.alt.base.BaseContractPresenter
import makes.flint.alt.base.BaseContractView
import makes.flint.alt.data.snapshot.SnapShot

/**
 * SnapShotContract
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
interface SnapShotContractView: BaseContractView {
    fun displaySnapShots(snapShots: List<SnapShot>)
}

interface SnapShotContractPresenter: BaseContractPresenter<SnapShotContractView>{
}