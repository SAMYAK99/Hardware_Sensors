package com.projects.hardwaresensors

import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var sensorEvenetListner: SensorEventListener
    lateinit var sensorManager: SensorManager
    lateinit var ProximitySensor: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        accbtn.setOnClickListener {
            val i = Intent(this, AccelerometerSensor::class.java)


            startActivity(i)
        }



        sensorManager = getSystemService<SensorManager>()!!
        ProximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        sensorEvenetListner = object : SensorEventListener {

            override fun onSensorChanged(events: SensorEvent?) {
                Log.d(
                    "HW Sensor", """"onSensorsChanged : ${events!!.values[0]}
                    """.trimIndent()
                )
                if (events.values[0] <= 3)
                    window.decorView.setBackgroundColor(Color.BLUE)
                else if (events.values[0] <= 5)
                    window.decorView.setBackgroundColor(Color.GREEN)
                else if (events.values[0] <= 7)
                    window.decorView.setBackgroundColor(Color.MAGENTA)
                else
                    window.decorView.setBackgroundColor(Color.RED)
            }


            override fun onAccuracyChanged(events: Sensor?, p1: Int) {
                // nothing to be implemented beacuse sensors are different on the needs
            }

        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            sensorEvenetListner, ProximitySensor, 2 * 1000 * 1000,
        )
    }

    override fun onPause() {
        sensorManager.unregisterListener(sensorEvenetListner)
        super.onPause()

    }
}