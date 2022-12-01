package fr.cdudit.playgroundfinder.features.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import fr.cdudit.playgroundfinder.databinding.FragmentPlaygroundDetailBinding
import fr.cdudit.playgroundfinder.managers.ShareManager
import fr.cdudit.playgroundfinder.models.Record
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaygroundDetailFragment : Fragment() {
    private val viewModel: PlaygroundDetailViewModel by viewModel()
    private val args: PlaygroundDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentPlaygroundDetailBinding
    private lateinit var playground: Record

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentPlaygroundDetailBinding.inflate(layoutInflater, container, false)
        this.playground = args.playground
        this.initListeners()
        this.initUI()
        this.initMap()
        return this.binding.root
    }

    private fun initListeners() {
        val geoUri = this.playground.getGoogleMapsUri()

        this.binding.imageButtonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        this.binding.buttonItinerary.setOnClickListener {
            requireActivity().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(geoUri)))
        }
        this.binding.buttonShare.setOnClickListener {
            ShareManager.shareViaSMS(requireContext(), geoUri)
        }
    }

    private fun initMap() {
        val map = childFragmentManager.findFragmentById(this.binding.map.id) as SupportMapFragment?
        map?.getMapAsync {
            val markerLatLng = LatLng(playground.fields.geoPoint2d[0], playground.fields.geoPoint2d[1])
            it.addMarker(
                MarkerOptions()
                    .position(markerLatLng)
                    .title(playground.fields.siteName)
            )
            val cameraPosition = CameraPosition.builder()
                .target(markerLatLng)
                .zoom(16f)
                .build()
            it.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            it.uiSettings.isZoomControlsEnabled = true
        }
    }

    private fun initUI() {
        this.binding.textViewPlaygroundTitle.text = this.playground.fields.siteName
        this.binding.textViewDetailMinAge.text = getString(
            viewModel.getAgeResId(), playground.fields.ageMin.toInt(), playground.fields.ageMax.toInt()
        )
        this.binding.textViewDetailMaxAge.text = getString(
            viewModel.getGamesResId(), playground.fields.gameNumber
        )
        this.binding.textViewDetailSurface.text = getString(
            viewModel.getSurfaceResId(), playground.fields.surface.toInt()
        )
    }
}