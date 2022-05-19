package ru.mirea.istornikov.mireaproject.ui.map

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.places.PlacesFactory
import com.yandex.mapkit.places.panorama.NotFoundError
import com.yandex.mapkit.places.panorama.PanoramaService
import com.yandex.mapkit.places.panorama.PanoramaService.SearchSession
import com.yandex.mapkit.places.panorama.PanoramaView
import com.yandex.runtime.Error
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError
import ru.mirea.istornikov.mireaproject.R


class PanoramaActivity : AppCompatActivity(), PanoramaService.SearchListener {
    private val MAPKIT_API_KEY = "34e130d3-7abe-41fe-b0c2-7328026d23b4"
    private val SEARCH_LOCATION = Point(55.733330, 37.587649)

    private lateinit var panoramaView: PanoramaView
    private lateinit var panoramaService: PanoramaService
    private lateinit var searchSession: SearchSession

    init{
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.initialize(this)
        PlacesFactory.initialize(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panorama)

        panoramaView = findViewById<PanoramaView>(R.id.panoview)
        panoramaService = PlacesFactory.getInstance().createPanoramaService()
        searchSession = panoramaService.findNearest(SEARCH_LOCATION, this)
    }

    override fun onStop() {
        panoramaView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.getInstance().onStart()
        panoramaView.onStart()
    }

    override fun onPanoramaSearchResult(panoramaId: String) {
        panoramaView.player.openPanorama(panoramaId)
        panoramaView.player.enableMove()
        panoramaView.player.enableRotation()
        panoramaView.player.enableZoom()
        panoramaView.player.enableMarkers()
    }

    override fun onPanoramaSearchError(error: Error) {
        var errorMessage = getString(R.string.unknown_error_message)
        if (error is NotFoundError) {
            errorMessage = getString(R.string.not_found_error_message)
        } else if (error is RemoteError) {
            errorMessage = getString(R.string.remote_error_message)
        } else if (error is NetworkError) {
            errorMessage = getString(R.string.network_error_message)
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}