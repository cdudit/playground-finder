package fr.cdudit.playgroundfinder.features.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import fr.cdudit.playgroundfinder.databinding.FragmentPlaygroundListBinding
import fr.cdudit.playgroundfinder.models.Record
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaygroundListFragment : Fragment() {
    private lateinit var binding: FragmentPlaygroundListBinding
    private val viewModel: PlaygroundListViewModel by viewModel()
    private val playgrounds = ArrayList<Record>()
    private lateinit var adapter: PlaygroundListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentPlaygroundListBinding.inflate(layoutInflater, container, false)
        this.initRecyclerView()
        this.initListeners()
        this.getPlaygrounds()
        return this.binding.root
    }

    private fun initListeners() {
        this.binding.imageButtonFilter.setOnClickListener {
            PlaygroundListFilterBottomSheetFragment().show(
                childFragmentManager, PlaygroundListFilterBottomSheetFragment.TAG
            )
        }
    }

    private fun initRecyclerView() {
        this.adapter = PlaygroundListAdapter(requireContext(), playgrounds)
        this.binding.recyclerViewPlaygrounds.adapter = this.adapter
    }

    private fun getPlaygrounds() {
        this.binding.progressBar.isVisible = true
        this.viewModel.getPlaygrounds(
            onSuccess = { playgroundApi ->
                playgroundApi?.records?.let {
                    playgrounds.addAll(it)
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
}