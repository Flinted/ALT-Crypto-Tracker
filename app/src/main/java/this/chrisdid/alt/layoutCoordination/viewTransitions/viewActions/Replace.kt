package this.chrisdid.alt.layoutCoordination.viewTransitions.viewActions

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction

/**
 * Replace
 */
class Replace<T: Fragment>(private val targetId: Int,
                           private val classId: Class<T>,
                           private val arguments: Bundle? = null): ViewAction<FragmentTransaction> {

    override fun execute(executor: FragmentTransaction) {
        val fragment = classId.newInstance()
        if(arguments != null) {
            fragment.arguments = arguments
        }
        executor.replace(targetId, fragment)
    }
}
