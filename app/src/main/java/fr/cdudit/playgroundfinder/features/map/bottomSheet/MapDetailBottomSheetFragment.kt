package fr.cdudit.playgroundfinder.features.map.bottomSheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.cdudit.playgroundfinder.databinding.PlaygroundMapBottomSheetBinding
import fr.cdudit.playgroundfinder.managers.ShareManager
import fr.cdudit.playgroundfinder.models.Record
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapDetailBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: PlaygroundMapBottomSheetBinding
    private lateinit var playground: Record
    private lateinit var onFavoriteClick: (Boolean) -> Unit
    private val viewModel: MapDetailBottomSheetViewModel by viewModel()

    companion object {
        const val TAG = "MapDetailBottomSheetFragment"

        fun newInstance(
            record: Record,
            onFavoriteClick: (Boolean) -> Unit
        ): MapDetailBottomSheetFragment {
            return MapDetailBottomSheetFragment().apply {
                this.playground = record
                this.onFavoriteClick = onFavoriteClick
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = PlaygroundMapBottomSheetBinding.inflate(layoutInflater, container, false)
        this.initUI()
        this.initListeners()
        return this.binding.root
    }

    private fun initUI() {
        this.binding.textViewTitle.text = playground.fields.siteName
        this.binding.textViewDetailMinAge.text = getString(
            viewModel.getAgeResId(), playground.fields.ageMin.toInt(), playground.fields.ageMax.toInt()
        )
        this.binding.textViewDetailMaxAge.text = getString(
            viewModel.getGamesResId(), playground.fields.gameNumber
        )
        this.binding.textViewDetailSurface.text = getString(
            viewModel.getSurfaceResId(), playground.fields.surface.toInt()
        )
        this.setFavoriteAlpha()
    }

    private fun initListeners() {
        val geoUri = this.playground.getGoogleMapsUri()

        this.binding.buttonItinerary.setOnClickListener {
            requireActivity().startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
            )
        }

        this.binding.imageButtonShare.setOnClickListener {
            ShareManager.shareViaSMS(requireContext(), geoUri)
        }

        this.binding.imageButtonFavorite.setOnClickListener {
            this.playground.isFavorite = !this.playground.isFavorite
            this.setFavoriteAlpha()
            onFavoriteClick(this.playground.isFavorite)
        }
    }

    private fun setFavoriteAlpha() {
        this.binding.imageButtonFavorite.alpha = if (this.playground.isFavorite) 1f else 0.2f
    }
}