package ru.mirea.istornikov.multyactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val name = findViewById<TextView>(R.id.name)
        val savedName = intent.getSerializableExtra("name")
        name.text = "Ваше имя - $savedName"
    }
}