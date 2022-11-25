package fr.cdudit.playgroundfinder.features.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.cdudit.playgroundfinder.databinding.PlaygroundListFiltersBinding

class PlaygroundListFilterBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: PlaygroundListFiltersBinding

    companion object {
        const val TAG = "PlaygroundListFilterBottomSheetFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = PlaygroundListFiltersBinding.inflate(layoutInflater, container, false)
        return this.binding.root
    }
}