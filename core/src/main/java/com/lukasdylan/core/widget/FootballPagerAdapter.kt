package com.lukasdylan.core.widget

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FootballPagerAdapter(fragmentManager: FragmentManager,
                           private val fragmentList: List<Pair<String, Fragment>>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = fragmentList[position].second

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = fragmentList[position].first
}