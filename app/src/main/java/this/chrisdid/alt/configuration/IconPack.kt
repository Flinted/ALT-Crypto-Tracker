package this.chrisdid.alt.configuration

import this.chrisdid.alt.R
import this.chrisdid.alt.data.coinListItem.*

interface IconPack {
    val upExtreme: Int
    val upSignificant: Int
    val upModerate: Int
    val downExtreme: Int
    val downSignificant: Int
    val downModerate: Int
    val unknown: Int
    val neutral: Int

    fun getColor(status: Int): Int {
        return when (status) {
            CHANGE_DOWN_MODERATE, CHANGE_DOWN_SIGNIFICANT, CHANGE_DOWN_EXTREME, CHANGE_STATIC_NEGATIVE
                           -> R.color.colorRed
            CHANGE_UNKNOWN -> R.color.colorOffBlack
            else           -> R.color.colorGreen
        }
    }
}