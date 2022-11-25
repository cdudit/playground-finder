package fr.cdudit.playgroundfinder.features.tabbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import fr.cdudit.playgroundfinder.adapters.TabPageAdapter
import fr.cdudit.playgroundfinder.databinding.FragmentHomeBinding
import fr.cdudit.playgroundfinder.databinding.FragmentTabBarBinding

class TabBarFragment : Fragment() {

    private lateinit var binding: FragmentTabBarBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentTabBarBinding.inflate(layoutInflater, container, false)
        setUpTabBar()
        return this.binding.root
    }


    private fun setUpTabBar() {
        val adapter = TabPageAdapter(this.requireActivity() , binding.tabLayout.tabCount)
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}