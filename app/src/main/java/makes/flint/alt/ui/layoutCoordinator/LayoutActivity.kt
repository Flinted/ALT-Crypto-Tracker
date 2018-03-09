package makes.flint.alt.ui.layoutCoordinator

import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.widget.FrameLayout
import makes.flint.alt.R
import makes.flint.alt.base.BaseActivity

/**
 * LayoutActivity
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class LayoutActivity : BaseActivity() {

    private lateinit var masterLayout: ConstraintLayout
    private lateinit var frameA: FrameLayout
    private lateinit var frameB: FrameLayout
    private lateinit var frameC: FrameLayout
    private lateinit var popFrameA: FrameLayout
    private lateinit var popFrameB: FrameLayout
    private lateinit var fab: FloatingActionButton

    private lateinit var coordinator: LayoutCoordinator
    private var currentViewState = loading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template)
        bindViews()
        setOnClick()
        coordinator = LayoutCoordinator()
        coordinator.initialiseConstraintsFor(masterLayout)
        loadMarketFragment()
    }

    private fun loadMarketFragment() {
        Handler().postDelayed({
            fab.callOnClick()
        }, 2000)
    }

    private fun setOnClick() {
        fab.setOnClickListener {
            val viewKey = when (currentViewState) {
                loading -> home
                home -> coin
                else -> home
            }
            currentViewState = viewKey
            coordinator.changeConstraints(viewKey, masterLayout)
        }
    }

    private fun bindViews() {
        masterLayout = findViewById(R.id.master_layout)
        frameA = findViewById(R.id.frame_a)
        frameB = findViewById(R.id.frame_b)
        frameC = findViewById(R.id.frame_c)
        popFrameA = findViewById(R.id.pop_frame_a)
        popFrameB = findViewById(R.id.pop_frame_b)
        fab = findViewById(R.id.layout_fab)
    }
}
