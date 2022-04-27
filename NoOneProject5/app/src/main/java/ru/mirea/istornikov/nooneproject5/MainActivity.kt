package ru.mirea.istornikov.nooneproject5

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter

class MainActivity : AppCompatActivity() , SensorEventListener{

    private lateinit var sensorManager: SensorManager
    private lateinit var listCountSensor: ListView

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listCountSensor = findViewById(R.id.list_view)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors:List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val arrayList:ArrayList<HashMap<String, Any>> = ArrayList()
        var sensorTypeList:HashMap<String, Any>
        for ( i in sensors.indices){
            sensorTypeList = HashMap()
            sensorTypeList["Name"] = sensors[i].name as Any
            sensorTypeList["Value"] = sensors[i].maximumRange as Any
            arrayList.add(sensorTypeList)
        }
        val mHistory = SimpleAdapter(
            this,
            arrayList,
            android.R.layout.simple_list_item_2,
            arrayOf("Name", "Value") ,
            intArrayOf(android.R.id.text1, android.R.id.text2))
        listCountSensor.adapter = mHistory

        val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            if(p0.sensor.type == Sensor.TYPE_ACCELEROMETER ){
                var valueAzimuth = p0.values[0]
                var valuePitch = p0.values[1]
                var valueRoll = p0.values[2]
            }

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }
}