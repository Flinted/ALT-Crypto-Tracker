package did.chris.alt.ui.dyor

import android.view.View
import android.widget.TextView
import did.chris.alt.R

class DYORBottomSheetViewHolder(view: View) {

    internal var googleButton: TextView = view.findViewById(R.id.DYOR_google)
    internal var facebookButton: TextView = view.findViewById(R.id.DYOR_facebook)
    internal var githubButton: TextView = view.findViewById(R.id.DYOR_github)
    internal var cmcButton: TextView = view.findViewById(R.id.DYOR_coinmarketcap)
}
