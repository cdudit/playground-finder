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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import fr.cdudit.playgroundfinder.databinding.FragmentMapBinding
import fr.cdudit.playgroundfinder.features.map.bottomSheet.MapDetailBottomSheetFragment
import fr.cdudit.playgroundfinder.models.Record
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: FragmentMapBinding
    private val viewModel: MapViewModel by viewModel()
    private val records = arrayListOf<Record>()

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
            this.viewModel.getPlaygrounds(
                onSuccess = { playgroundApi ->
                    playgroundApi?.records?.let { records ->
                        this.records.addAll(records)
                        records.forEach {
                            val marker = map.addMarker(
                                MarkerOptions()
                                    .position(LatLng(it.fields.geoPoint2d[0], it.fields.geoPoint2d[1]))
                                    .title(it.fields.siteName)
                            )
                            marker?.tag = it.recordId
                        }
                    }
                },
                onError = {
                    Toast.makeText(requireContext(), it?.string(), Toast.LENGTH_LONG).show()
                }
            )
            this.setupMapUI(map)
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

    override fun onMarkerClick(marker: Marker): Boolean {
        records.find { marker.tag == it.recordId }?.let {
            val bottomSheet = MapDetailBottomSheetFragment.newInstance(it)
            bottomSheet.show(childFragmentManager, MapDetailBottomSheetFragment.TAG)
        }
        return false
    }
}