package fr.cdudit.playgroundfinder.features.tabbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
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
        //Desactive la possibilité de retourner au SplashScreen
        this.requireActivity().onBackPressedDispatcher.addCallback(this){}
        return this.binding.root
    }


    private fun setUpTabBar() {
        val adapter = TabBarAdapter(this.requireActivity() , binding.tabLayout.tabCount)
        binding.viewPager.adapter = adapter

        binding.viewPager.isUserInputEnabled = false

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