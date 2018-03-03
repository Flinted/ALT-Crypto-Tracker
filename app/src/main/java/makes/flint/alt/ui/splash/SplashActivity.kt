package makes.flint.alt.ui.splash

import android.content.Intent
import android.os.Bundle
import makes.flint.alt.base.BaseActivity
import makes.flint.alt.ui.main.MainActivity

/**
 * SplashActivity
 * Copyright © 2018 Flint Makes. All rights reserved.
 */
class SplashActivity : BaseActivity(), SplashContractView {

    // Properties

    private lateinit var splashPresenter: SplashContractPresenter

    // Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.splashPresenter = getPresenterComponent().provideSplashPresenter()
        splashPresenter.attachView(this)
        this.attachPresenter(splashPresenter)
        splashPresenter.initialise()
    }

    // Overrides

    override fun proceedToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
