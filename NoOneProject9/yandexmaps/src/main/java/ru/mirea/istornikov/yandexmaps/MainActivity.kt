package ru.mirea.istornikov.yandexmaps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider


class MainActivity : AppCompatActivity(), UserLocationObjectListener {
    private lateinit var mapView: MapView
    private val MAPKIT_API_KEY = "34e130d3-7abe-41fe-b0c2-7328026d23b4"
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private var isWork = false
    private var userLocationLayer: UserLocationLayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapview)
        isWork = hasPermissions(this, *PERMISSIONS)
        if (isWork) {
            loadUserLocationLayer()
        } else {
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS, REQUEST_CODE_PERMISSION
            )
        }
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
                this, android.R.drawable.star_big_on
            )
        )
        userLocationView.pin.setIcon(
            ImageProvider.fromResource(
                this, android.R.drawable.ic_menu_mylocation
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