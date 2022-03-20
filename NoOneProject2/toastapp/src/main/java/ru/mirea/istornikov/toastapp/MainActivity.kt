package ru.mirea.istornikov.toastapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun toast(view: View){
        val toast = Toast.makeText(
            applicationContext,
            "Здравствуй MIREA! Istornikov Andrey",
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        val toastContainer = toast.view as LinearLayout?
        toast.show()
    }
}