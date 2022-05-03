package ru.mirea.istornikov.livedata

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ru.mirea.istornikov.livedata.TimeController.setTime
import ru.mirea.istornikov.livedata.TimeController.time


class MainActivity : AppCompatActivity(), Observer<Long?> {
    private lateinit var networkNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        networkNameTextView = findViewById(R.id.textView)
        time.observe(this, this)
        val handler = Handler()
        handler.postDelayed({ setTime() }, 5000)
    }

    override fun onChanged(s: Long?) {
        Log.d(MainActivity::class.java.simpleName, s.toString() + "")
        networkNameTextView!!.text = "" + s
    }
}