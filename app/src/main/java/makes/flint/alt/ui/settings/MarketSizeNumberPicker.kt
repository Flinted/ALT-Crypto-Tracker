package makes.flint.alt.ui.settings

import android.content.Context
import android.util.AttributeSet
import android.widget.NumberPicker

/**
 * MarketSizeNumberPicker
 */
class MarketSizeNumberPicker : NumberPicker {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        setValues()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setValues()
    }

    private fun setValues() {
        minValue = 1
        maxValue = 20
        setFormatter {
            (it * 100).toString()
        }
        value = 15
        invalidate()
    }


}