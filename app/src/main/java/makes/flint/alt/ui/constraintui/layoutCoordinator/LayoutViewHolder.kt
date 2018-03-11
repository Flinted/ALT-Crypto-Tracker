package makes.flint.alt.ui.constraintui.layoutCoordinator

import android.support.constraint.ConstraintLayout
import android.widget.FrameLayout
import android.widget.TextView
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
    internal var loadingMessage: TextView = activity.findViewById(R.id.pop_frame_loading_textview)
}
