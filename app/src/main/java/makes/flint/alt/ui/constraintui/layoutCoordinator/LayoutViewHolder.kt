package makes.flint.alt.ui.constraintui.layoutCoordinator

import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.widget.FrameLayout
import makes.flint.alt.R

/**
 * LayoutViewHolder
 * Copyright © 2018 ChrisDidThis. All rights reserved.
 */
class LayoutViewHolder(activity: LayoutActivity) {
    internal var masterLayout: ConstraintLayout = activity.findViewById(R.id.master_layout)
    internal var frameTop: FrameLayout = activity.findViewById(R.id.frame_top)
    internal var frameCentre: FrameLayout = activity.findViewById(R.id.frame_centre)
    internal var frameBottom: FrameLayout = activity.findViewById(R.id.frame_bottom)
    internal var popFrameTop: FrameLayout = activity.findViewById(R.id.pop_frame_top)
    internal var popFrameBottom: FrameLayout = activity.findViewById(R.id.pop_frame_bottom)
    internal var fab: FloatingActionButton = activity.findViewById(R.id.layout_fab)
}
