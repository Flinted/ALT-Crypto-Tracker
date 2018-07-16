package did.chris.alt.configuration

import did.chris.alt.R
import did.chris.alt.data.coinListItem.*

interface IconPack {

    // Properties
    val upExtreme: Int
    val upSignificant: Int
    val upModerate: Int
    val downExtreme: Int
    val downSignificant: Int
    val downModerate: Int
    val unknown: Int
    val neutral: Int

    //Functions
    fun getColor(status: Int): Int {
        return when (status) {
            CHANGE_DOWN_MODERATE, CHANGE_DOWN_SIGNIFICANT, CHANGE_DOWN_EXTREME, CHANGE_STATIC_NEGATIVE
                           -> R.color.colorRed
            CHANGE_UNKNOWN -> R.color.colorOffBlack
            else           -> R.color.colorGreen
        }
    }
}