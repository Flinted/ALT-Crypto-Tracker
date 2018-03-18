package makes.flint.alt.layoutCoordination.viewTransitions.viewActions

import android.support.constraint.ConstraintLayout
import android.widget.FrameLayout
import org.jetbrains.anko.find

/**
 * Clear
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class Clear(private val targetId: Int): ViewAction<ConstraintLayout> {

    override fun execute(executor: ConstraintLayout) {
        val targetFrame: FrameLayout = executor.find(targetId)
        targetFrame.removeAllViews()
    }
}