package ru.mirea.istornikov.mireaproject.ui.map

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import ru.mirea.istornikov.mireaproject.R
import ru.mirea.istornikov.mireaproject.databinding.FragmentMapBinding
import ru.mirea.istornikov.mireaproject.databinding.FragmentWebParserBinding

class MapFragment : Fragment(), UserLocationObjectListener {
    private val MAPKIT_API_KEY = "34e130d3-7abe-41fe-b0c2-7328026d23b4"
    lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var userLocationLayer: UserLocationLayer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(requireContext())

        loadUserLocationLayer()

        mapView = binding.mapview
        return root
    }


    private fun loadUserLocationLayer() {
        val mapKit = MapKitFactory.getInstance()
        userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
        userLocationLayer!!.isVisible = true
        userLocationLayer!!.isHeadingEnabled = true
        userLocationLayer!!.setObjectListener(this)
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        userLocationLayer!!.setAnchor(
            PointF((mapView.width * 0.5).toFloat(), (mapView.height * 0.5).toFloat()),
            PointF((mapView.width * 0.5).toFloat(), (mapView.height * 0.83).toFloat())
        )
        userLocationView.arrow.setIcon(
            ImageProvider.fromResource(
                requireContext(), android.R.drawable.star_big_on
            )
        )
        userLocationView.pin.setIcon(
            ImageProvider.fromResource(
                requireContext(), android.R.drawable.ic_menu_mylocation
            )
        )
        userLocationView.accuracyCircle.fillColor = Color.BLUE
    }

    override fun onObjectUpdated(userLocationView: UserLocationView, objectEvent: ObjectEvent) {}
    override fun onObjectRemoved(userLocationView: UserLocationView) {}

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

}