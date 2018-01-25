package makes.flint.poh.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import makes.flint.poh.R
import makes.flint.poh.base.BaseActivity

/**
 * MainActivity
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class MainActivity: BaseActivity(), MainContractView {
    // View Bindings

    // Private Properties
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
        println("HERE WE GO!")
        mainPresenter = getPresenterComponent().provideMainPresenter()
        mainPresenter.attachView(this)
        attachPresenter(mainPresenter)
        mainPresenter.initialise()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        println("OPTIONS")
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId ?: return false
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Private Functions
    private fun bindViews() {
    }
}