package fr.cdudit.playgroundfinder.features.map.bottomSheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.cdudit.playgroundfinder.databinding.PlaygroundMapBottomSheetBinding
import fr.cdudit.playgroundfinder.models.Record
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapDetailBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: PlaygroundMapBottomSheetBinding
    private var playground: Record? = null
    private val viewModel: MapDetailBottomSheetViewModel by viewModel()

    companion object {
        const val TAG = "MapDetailBottomSheetFragment"

        fun newInstance(record: Record): MapDetailBottomSheetFragment {
            return MapDetailBottomSheetFragment().apply {
                this.playground = record
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
        this.initListener()
        return this.binding.root
    }

    private fun initUI() {
        playground?.let {
            this.binding.textViewTitle.text = it.fields.siteName
            this.binding.textViewDetailMinAge.text = getString(
                viewModel.getAgeResId(), it.fields.ageMin.toInt(), it.fields.ageMax.toInt()
            )
            this.binding.textViewDetailMaxAge.text = getString(
                viewModel.getGamesResId(), it.fields.gameNumber
            )
            this.binding.textViewDetailSurface.text = getString(
                viewModel.getSurfaceResId(), it.fields.surface.toInt()
            )
        }
    }

    private fun initListener() {
        this.binding.buttonItinerary.setOnClickListener {
            playground?.let {
                val geoUri = "http://maps.google.com/maps?q=loc:${it.fields.geoPoint2d[0]},${it.fields.geoPoint2d[1]}"
                requireActivity().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(geoUri)))
            }
        }
    }
}