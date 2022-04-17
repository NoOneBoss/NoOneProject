package ru.mirea.istornikov.nooneproject3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val dateTextView = findViewById<TextView>(R.id.dateText)
        dateTextView.text = intent.getStringExtra("time")
    }
}