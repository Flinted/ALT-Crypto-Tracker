package makes.flint.alt.ui.constraintui.layoutCoordinator

import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.widget.FrameLayout
import makes.flint.alt.R

/**
 * LayoutViewHolder
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class LayoutViewHolder(activity: LayoutActivity) {
    internal var masterLayout: ConstraintLayout = activity.findViewById(R.id.master_layout)
    internal var frameTop: FrameLayout = activity.findViewById(R.id.frame_a)
    internal var frameCentre: FrameLayout = activity.findViewById(R.id.frame_b)
    internal var frameBottom: FrameLayout = activity.findViewById(R.id.frame_c)
    internal var popFrameTop: FrameLayout = activity.findViewById(R.id.pop_frame_a)
    internal var popFrameBottom: FrameLayout = activity.findViewById(R.id.pop_frame_b)
    internal var fab: FloatingActionButton = activity.findViewById(R.id.layout_fab)
}
