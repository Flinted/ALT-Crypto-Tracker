package makes.flint.alt.ui.splash

import android.content.Intent
import android.os.Bundle
import makes.flint.alt.base.BaseActivity
import makes.flint.alt.ui.main.MainActivity
import makes.flint.alt.ui.onboard.OnboardActivity

/**
 * SplashActivity
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SplashActivity : BaseActivity(), SplashContractView {

    private lateinit var splashPresenter: SplashContractPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.splashPresenter = getPresenterComponent().provideSplashPresenter()
        splashPresenter.attachView(this)
        this.attachPresenter(splashPresenter)
        splashPresenter.initialise()
    }

    override fun proceedToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
