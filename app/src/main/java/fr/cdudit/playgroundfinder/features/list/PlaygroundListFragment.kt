package fr.cdudit.playgroundfinder.features.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import fr.cdudit.playgroundfinder.R
import fr.cdudit.playgroundfinder.databinding.FragmentPlaygroundListBinding
import fr.cdudit.playgroundfinder.features.tabbar.TabBarFragmentDirections
import fr.cdudit.playgroundfinder.models.Record
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaygroundListFragment : Fragment(), NavController.OnDestinationChangedListener {
    private lateinit var binding: FragmentPlaygroundListBinding
    private val viewModel: PlaygroundListViewModel by viewModel()
    private var playgrounds = ArrayList<Record>()
    private lateinit var adapter: PlaygroundListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentPlaygroundListBinding.inflate(layoutInflater, container, false)
        this.initRecyclerView()
        this.initListeners()
        this.getPlaygrounds()
        findNavController().addOnDestinationChangedListener(this)
        return this.binding.root
    }

    /**
     * Update playgrounds favorites when switch tab
     */
    override fun onResume() {
        super.onResume()
        if (this.playgrounds.isNotEmpty()) {
            this.playgrounds = this.viewModel.mapWithFavorites(requireContext(), this.playgrounds) as ArrayList<Record>
            this.adapter.notifyItemRangeChanged(0, this.playgrounds.size)
        }
    }

    /**
     * Update playgrounds favorites when `popBackStack()` executed from `PlaygroundDetailFragment`
     */
    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        if (destination.id == R.id.tabBarFragment && !this.isHidden) {
            onResume()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        findNavController().removeOnDestinationChangedListener(this)
    }

    private fun initListeners() {
        val bottomSheetFragment = PlaygroundListFilterBottomSheetFragment.newInstance { ageMin, ageMax, search ->
            getPlaygrounds(ageMin, ageMax, search)
        }
        this.binding.imageButtonFilter.setOnClickListener {
            bottomSheetFragment.show(childFragmentManager, PlaygroundListFilterBottomSheetFragment.TAG)
        }
    }

    private fun initRecyclerView() {
        this.adapter = PlaygroundListAdapter(requireContext(), playgrounds) {
            findNavController().navigate(
                TabBarFragmentDirections.navigateToDetailFragment(it)
            )
        }
        this.binding.recyclerViewPlaygrounds.adapter = this.adapter
    }

    private fun getPlaygrounds(ageMin: Int? = null, ageMax: Int? = null, search: String? = null) {
        clearList()
        this.binding.progressBar.isVisible = true
        this.viewModel.getPlaygrounds(
            ageMin,
            ageMax,
            search,
            onSuccess = { records ->
                records?.let {
                    playgrounds.addAll(this.viewModel.mapWithFavorites(requireContext(), it))
                    this.adapter.notifyItemRangeInserted(0, it.size)
                }
                this.binding.progressBar.isVisible = false
            },
            onError = {
                Toast.makeText(requireContext(), it?.string(), Toast.LENGTH_LONG).show()
                this.binding.progressBar.isVisible = false
            }
        )
    }

    private fun clearList() {
        val removedSize = this.playgrounds.size
        this.playgrounds.clear()
        this.adapter.notifyItemRangeRemoved(0, removedSize)
    }
}