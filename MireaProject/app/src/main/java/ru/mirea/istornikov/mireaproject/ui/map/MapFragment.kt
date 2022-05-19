package ru.mirea.istornikov.mireaproject.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.runtime.image.ImageProvider
import ru.mirea.istornikov.mireaproject.R
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

    init{
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

        binding.zoomIn.setOnClickListener(this::zoomInClick)
        binding.zoomOut.setOnClickListener(this::zoomOutClick)
        return root
    }

    fun zoomInClick(view: View) {
        val zoom = if(mapView.map.cameraPosition.zoom + 1 <= mapView.map.maxZoom) mapView.map.cameraPosition.zoom + 1 else mapView.map.maxZoom
        mapView.map.move(CameraPosition(mapView.map.cameraPosition.target, zoom, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0.2F),
            null)
    }

    fun zoomOutClick(view: View) {
        val zoom = if(mapView.map.cameraPosition.zoom - 1 >= mapView.map.minZoom) mapView.map.cameraPosition.zoom - 1 else mapView.map.minZoom
        mapView.map.move(CameraPosition(mapView.map.cameraPosition.target, zoom, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0.2F),
            null)
    }


    private fun loadUserLocationLayer() {
        val mapKit = MapKitFactory.getInstance()
        userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
        userLocationLayer!!.isVisible = true
        userLocationLayer!!.isHeadingEnabled = true
        mapView.map.move(
            CameraPosition(Point(55.794292, 37.701564), 8.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )
        addPlacemark(Point(55.794292, 37.701564), R.drawable.ic_location,
            "ул. Стромынка, 20, Москва",
            "Название: МГУПИ\nКоординаты: 55.794292, 37.701564\nДата основания:1936 г.")

        addPlacemark(Point(55.661733, 37.477908), R.drawable.ic_location,
            "просп. Вернадского, 86, Москва",
            "Название: МИТХТ\nКоординаты: 55.661733, 37.477908\nДата основания:1 июля 1900 г.")

        addPlacemark(Point(55.669803, 37.479429), R.drawable.ic_location,
            "просп. Вернадского, 78, Москва",
            "Название: МИРЭА\nКоординаты: 55.669803, 37.479429\nДата основания: 1947 г.")

        addPlacemark(Point(55.731457, 37.574415), R.drawable.ic_location,
            "Малая Пироговская улица, 1с5, Москва",
            "Название: МИРЭА\nКоординаты: 55.731457, 37.574415\nДата основания: 1 июля 1900 г.")

        addPlacemark(Point(55.764925, 37.742172), R.drawable.ic_location,
            "5-я улица Соколиной Горы, 22, Москва",
            "Название: МИРЭА\nКоординаты: 55.764925, 37.742172\nДата основания: 1 июля 1900 г.")

        addPlacemark(Point(45.052221, 41.912577), R.drawable.ic_location,
            "проспект Кулакова, 8литА, Ставрополь",
            "Название: Филиал в г. Ставрополе\nКоординаты: 45.052221, 41.912577\nДата основания: 18 декабря 1996 г.")

        addPlacemark(Point(55.966766, 38.050778), R.drawable.ic_location,
            "Вокзальная ул., 2А, корп. 61, Фрязино",
            "Название: Филиал в г. Фрязино\nКоординаты: 55.966766, 38.050778\nДата основания: 1962 г.")

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

    private fun addPlacemark(point: Point, image : Int, dialogTitle: String, dialogText: String){
        val placemark = mapView.map.mapObjects.addPlacemark(point)
        placemark.setIcon(ImageProvider.fromBitmap(requireContext().getBitmap(image)))

        placemark.addTapListener { mapObject, point ->
            Dialog(dialogTitle, dialogText, point).show(requireFragmentManager(),"1")
            return@addTapListener true
        }
    }

    fun Context.getBitmap(drawableId: Int): Bitmap? {
        var drawable = ContextCompat.getDrawable(this, drawableId) ?: return null

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable).mutate()
        }

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888) ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
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