package makes.flint.alt.ui.main

import android.app.Activity
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import com.stephentuso.welcome.WelcomeHelper
import makes.flint.alt.R
import makes.flint.alt.ui.interfaces.ActivityViewHolder
import makes.flint.alt.ui.onboard.OnboardActivity

/**
 * MainActivityViewHolder
 * Copyright © 2018 Intelligent Loyalty Limited. All rights reserved.
 */
class MainActivityViewHolder(activity: Activity) : ActivityViewHolder {

    internal var bottomBar: BottomNavigationView = activity.findViewById(R.id.navigation_bottom_bar)
    internal var viewPager: ViewPager = activity.findViewById(R.id.fragment_container)
    internal var toolbar: Toolbar = activity.findViewById(R.id.toolbar)
    internal var welcomeScreen = WelcomeHelper(activity, OnboardActivity::class.java)
    internal lateinit var searchView: SearchView

    init {
        (activity as MainActivity).setSupportActionBar(toolbar)
    }
}