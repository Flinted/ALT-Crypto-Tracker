package did.chris.alt.ui.dyor

import android.view.View
import android.widget.TextView
import did.chris.alt.R

class DYORBottomSheetViewHolder(view: View) {

    // Properties
    internal val googleButton: TextView = view.findViewById(R.id.DYOR_google)
    internal val facebookButton: TextView = view.findViewById(R.id.DYOR_facebook)
    internal val githubButton: TextView = view.findViewById(R.id.DYOR_github)
    internal val cmcButton: TextView = view.findViewById(R.id.DYOR_coinmarketcap)
}
