package makes.flint.alt.ui.onboard

import com.stephentuso.welcome.BasicPage
import com.stephentuso.welcome.TitlePage
import com.stephentuso.welcome.WelcomeActivity
import com.stephentuso.welcome.WelcomeConfiguration
import makes.flint.alt.R

/**
 * OnBoardActivity
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class OnBoardActivity : WelcomeActivity() {

    // Lifecycle
    override fun configuration(): WelcomeConfiguration {
        return WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.colorPrimary)
                .page(TitlePage(R.drawable.ic_market_24dp, "WELCOME"))
                .page(BasicPage(R.drawable.ic_tracker_24dp, "TRACKER", "TRACKER BUMPF GOES HERE"))
                .swipeToDismiss(true)
                .build()

    }
}