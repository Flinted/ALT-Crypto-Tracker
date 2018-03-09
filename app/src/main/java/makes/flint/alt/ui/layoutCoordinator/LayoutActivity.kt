package makes.flint.alt.ui.layoutCoordinator

import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.FloatingActionButton
import android.transition.TransitionManager
import android.view.View
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

    private lateinit var currentConstraintSet: ConstraintSet
    private var constraintInitialising: ConstraintSet = ConstraintSet()
    private var constraintHome: ConstraintSet = ConstraintSet()
    private var constraintCoin: ConstraintSet = ConstraintSet()
    private var constraintAdd: ConstraintSet = ConstraintSet()
    private var constraintPortfolio: ConstraintSet = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template)
        bindViews()
        setConstraintSets()
        configureHomeConstraint()
        configureCoinConstraint()
        setOnClick()
        loadMarketFragment()
    }

    private fun loadMarketFragment() {
        Handler().postDelayed({
            fab.callOnClick()
        }, 2000)
    }

    private fun setOnClick() {
        fab.setOnClickListener {
            val newConstraint = when (currentConstraintSet) {
                constraintHome -> constraintCoin
                else -> constraintHome
            }
            currentConstraintSet = newConstraint
            TransitionManager.beginDelayedTransition(masterLayout)
            newConstraint.applyTo(masterLayout)
        }
    }

    private fun configureCoinConstraint() {
        constraintCoin.apply {
            connect(R.id.layout_fab, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            setVisibility(R.id.frame_b, View.GONE)
            clear(R.id.frame_a)
            connect(R.id.frame_a, ConstraintSet.BOTTOM, R.id.frame_c, ConstraintSet.TOP)
            connect(R.id.frame_a, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(R.id.frame_a, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            connect(R.id.frame_a, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            connect(R.id.pop_frame_b, ConstraintSet.TOP, R.id.frame_c, ConstraintSet.TOP, 50)
            setMargin(R.id.pop_frame_b, ConstraintSet.BOTTOM, 50)
            setMargin(R.id.pop_frame_b, ConstraintSet.START, 50)
            setMargin(R.id.pop_frame_b, ConstraintSet.END, 50)
            setElevation(R.id.pop_frame_b, 10f)
            setVisibility(R.id.pop_frame_b, View.VISIBLE)
        }
    }

    private fun configureHomeConstraint() {
        constraintHome.apply {
            clear(R.id.frame_a)
            clear(R.id.frame_b)
            clear(R.id.frame_c)

            constrainHeight(R.id.frame_a, 300)
            connect(R.id.frame_a, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(R.id.frame_a, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            connect(R.id.frame_a, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

            constrainHeight(R.id.frame_c, 700)
            connect(R.id.frame_c, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            connect(R.id.frame_c, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            connect(R.id.frame_c, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

            connect(R.id.frame_b, ConstraintSet.BOTTOM, R.id.frame_c, ConstraintSet.TOP)
            connect(R.id.frame_b, ConstraintSet.TOP, R.id.frame_a, ConstraintSet.BOTTOM)
            connect(R.id.frame_b, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            connect(R.id.frame_b, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

            setVisibility(R.id.pop_frame_a, View.GONE)
            setVisibility(R.id.pop_frame_b, View.GONE)
            setVisibility(R.id.layout_fab, View.VISIBLE)
        }
    }

    private fun setConstraintSets() {
        constraintInitialising.clone(masterLayout)
        constraintHome.clone(masterLayout)
        configureHomeConstraint()
        constraintCoin.clone(constraintHome)
        constraintAdd.clone(constraintHome)
        constraintPortfolio.clone(constraintHome)
        currentConstraintSet = constraintInitialising
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
