package ru.mirea.istornikov.mireaproject.ui.webparser

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import ru.mirea.istornikov.mireaproject.databinding.FragmentWebParserBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*


class WebParserFragment : Fragment() {
    lateinit var binding: FragmentWebParserBinding
    lateinit var jsonObject: JSONObject

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWebParserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Build.VERSION.SDK_INT > 9) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        update(root)
        binding.update.setOnClickListener(this::update)
        return root
    }

    private fun update(view: View){
        val jsonParser = JSONParser()
        val url = URL("https://api.openweathermap.org/data/2.5/weather?id=524901&lang=en&units=metric&APPID=a50dad3843bc7540159c99aa4f5b589b")

        val connection = url.openConnection()
        val reader = BufferedReader(InputStreamReader(connection.getInputStream()))

        jsonObject = jsonParser.parse(reader) as JSONObject

        binding.weatherType.text = "Weather type: ${getMoscowWeatherType(jsonObject)}"
        binding.weatherTemp.text = "Temperature: ${getMoscowTemperature(jsonObject)}"
        binding.weatherTemp2.text = "Feels like: ${getMoscowFeelsLikeTemperature(jsonObject)}"
    }


    fun getMoscowWeatherType(jsonObject: JSONObject): String {
        val json = JSONParser().parse((jsonObject["weather"] as JSONArray)[0].toString()) as JSONObject
        return json["description"].toString().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    private fun getMoscowTemperature(jsonObject: JSONObject): String {
        val json = JSONParser().parse(jsonObject["main"].toString()) as JSONObject
        return json["temp"].toString()
    }

    private fun getMoscowFeelsLikeTemperature(jsonObject: JSONObject): String {
        val json = JSONParser().parse(jsonObject["main"].toString()) as JSONObject
        return json["feels_like"].toString()
    }
}