package fr.cdudit.playgroundfinder.features.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import fr.cdudit.playgroundfinder.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        getPlaygrounds()
        return this.binding.root
    }

    private fun getPlaygrounds() {
        this.viewModel.getPlaygrounds(
            onSuccess = { playgrounds ->
                playgrounds?.let {
                    Log.d("getPlaygrounds", it.toString())
                }
            },
            onError = {
                Toast.makeText(requireContext(), it?.string(), Toast.LENGTH_LONG).show()
            }
        )
    }
}