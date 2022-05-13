package ru.mirea.istornikov.mireaproject.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import ru.mirea.istornikov.mireaproject.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    private val MAPKIT_API_KEY = "34e130d3-7abe-41fe-b0c2-7328026d23b4"
    lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var userLocationLayer: UserLocationLayer

    private var isWork = false
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(requireContext())

        binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mapView = binding.mapview

        isWork = hasPermissions(requireActivity(), *PERMISSIONS)
        if (isWork) {
            loadUserLocationLayer()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                PERMISSIONS, REQUEST_CODE_PERMISSION
            )
        }
        return root
    }


    private fun loadUserLocationLayer() {
        val mapKit = MapKitFactory.getInstance()
        userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
        userLocationLayer!!.isVisible = true
        userLocationLayer!!.isHeadingEnabled = true
        mapView.map.move(
            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )
    }

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


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 100
        fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
            if (context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            permission!!
                        )
                        == PackageManager.PERMISSION_DENIED
                    ) return false
                }
                return true
            }
            return false
        }
    }

}