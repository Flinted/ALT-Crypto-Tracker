package makes.flint.alt.configuration

import makes.flint.alt.R

class SignalIconPack : IconPack {
    override val upExtreme = R.drawable.ic_signal_up_extreme_24dp
    override val upSignificant = R.drawable.ic_signal_up_significant_24dp
    override val upModerate = R.drawable.ic_signal_up_moderate_24dp
    override val downExtreme = R.drawable.ic_signal_down_extreme_24dp
    override val downSignificant = R.drawable.ic_signal_down_significant_24dp
    override val downModerate = R.drawable.ic_signal_down_moderate_24dp
    override val unknown = R.drawable.ic_change_unknown_24dp
    override val neutral: Int = R.drawable.ic_signal_neutral_24dp
}