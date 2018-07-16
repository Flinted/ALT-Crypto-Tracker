package did.chris.alt.layoutCoordination.viewTransitions.viewActions

import android.support.constraint.ConstraintLayout
import android.widget.FrameLayout
import org.jetbrains.anko.find

class Clear(private val targetId: Int): ViewAction<ConstraintLayout> {

    // Overrides
    override fun execute(executor: ConstraintLayout) {
        val targetFrame: FrameLayout = executor.find(targetId)
        targetFrame.removeAllViews()
    }
}