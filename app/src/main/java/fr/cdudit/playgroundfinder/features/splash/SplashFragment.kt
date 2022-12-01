package fr.cdudit.playgroundfinder.features.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import fr.cdudit.playgroundfinder.R
import fr.cdudit.playgroundfinder.features.tabbar.TabBarFragmentDirections

class SplashFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(
                SplashFragmentDirections.navigateToTabBarFragment()
            )
        }, 2000)

    }

}