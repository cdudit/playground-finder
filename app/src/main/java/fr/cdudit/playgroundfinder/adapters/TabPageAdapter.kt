package fr.cdudit.playgroundfinder.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.cdudit.playgroundfinder.features.home.HomeFragment
import fr.cdudit.playgroundfinder.features.map.MapFragment

class TabPageAdapter(activity: FragmentActivity, private  val tabCount: Int) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
       return when (position) {
           0 -> HomeFragment()
           1 -> MapFragment()
           else -> HomeFragment()
       }
    }

}