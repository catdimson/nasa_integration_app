package ru.dkotik.nasaintegrationapp.view.navigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

const val EARTH_KEY = 0
const val MARS_KEY = 1
const val SYSTEM_KEY = 2

class ViewPagerAdapter(private val fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(
        EarthFragment.newInstance(),
        MarsFragment.newInstance(),
        SystemFragment.newInstance()
    )

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            EARTH_KEY -> "Earth"
            MARS_KEY -> "Mars"
            SYSTEM_KEY -> "System"
            else -> "Earth"
        }
    }
}