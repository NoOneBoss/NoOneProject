package ru.mirea.istornikov.nooneproject3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startNewActivity(view: View){
        val format = "yyyy-MM-dd HH:mm:ss"
        val formatter = SimpleDateFormat(format)
        val dateString = formatter.format(Date(System.currentTimeMillis()))
        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("time", dateString)

        startActivity(intent)
    }
}