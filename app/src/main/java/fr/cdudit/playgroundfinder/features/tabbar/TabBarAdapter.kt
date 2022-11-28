package fr.cdudit.playgroundfinder.features.tabbar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.cdudit.playgroundfinder.features.list.PlaygroundListFragment
import fr.cdudit.playgroundfinder.features.map.MapFragment

class TabBarAdapter(activity: FragmentActivity, private  val tabCount: Int) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
       return when (position) {
           0 -> PlaygroundListFragment()
           1 -> MapFragment()
           else -> PlaygroundListFragment()
       }
    }
}