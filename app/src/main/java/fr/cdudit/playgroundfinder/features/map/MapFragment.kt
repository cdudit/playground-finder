package fr.cdudit.playgroundfinder.features.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import fr.cdudit.playgroundfinder.databinding.FragmentMapBinding
import fr.cdudit.playgroundfinder.features.map.bottomSheet.MapDetailBottomSheetFragment
import fr.cdudit.playgroundfinder.models.Record
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(), GoogleMap.OnMarkerClickListener {
    private lateinit var binding: FragmentMapBinding
    private val viewModel: MapViewModel by viewModel()
    private val records = arrayListOf<Record>()
    private val markers = arrayListOf<Marker>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(this.binding.map.id) as SupportMapFragment?

        mapFragment?.getMapAsync { map ->
            this.setupMapUI(map)

            this.viewModel.getPlaygrounds(
                onSuccess = { records ->
                    records?.let {
                        val mapped = this.viewModel.mapWithFavorites(requireContext(), it)
                        this.records.addAll(mapped)
                        this.addRecordsToMap(map, mapped)
                    }
                },
                onError = {
                    Toast.makeText(requireContext(), it?.string(), Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        if (this.records.isNotEmpty()) {
            this.records.clear()
            this.records.addAll(this.viewModel.mapWithFavorites(requireContext(), this.records))
            this.markers.forEach { marker ->
                this.records.find { it.recordId == marker.tag }?.let {
                    marker.setIcon(this.viewModel.getPinIcon(it))
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val favoritesId = this.records
            .filter { it.isFavorite }
            .map { it.recordId } as ArrayList<String>
        this.viewModel.setFavorites(requireContext(), favoritesId)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        records.find { marker.tag == it.recordId }?.let {
            val bottomSheet = MapDetailBottomSheetFragment.newInstance(it) { newIsFavorite ->
                it.isFavorite = newIsFavorite
                marker.setIcon(this.viewModel.getPinIcon(it))
            }
            bottomSheet.show(childFragmentManager, MapDetailBottomSheetFragment.TAG)
        }
        return false
    }

    private fun addRecordsToMap(map: GoogleMap, records: List<Record>) {
        records.forEach { record ->
            val marker = map.addMarker(
                MarkerOptions()
                    .position(LatLng(record.fields.geoPoint2d[0], record.fields.geoPoint2d[1]))
                    .title(record.fields.siteName)
                    .icon(this.viewModel.getPinIcon(record))
            )
            marker?.tag = record.recordId
            marker?.let { markers.add(it) }
        }
    }

    private fun setupMapUI(map: GoogleMap) {
        //set camera on Bordeaux
        val cameraPosition = CameraPosition.builder()
            .target(LatLng(44.837789, -0.57918))
            .zoom(11F)
            .build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
    }
}