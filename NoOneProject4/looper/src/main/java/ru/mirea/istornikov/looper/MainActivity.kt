package ru.mirea.istornikov.looper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View){
        val myLooper = MyLooper()
        myLooper.start()

        var msg = Message()
        val bundle = Bundle()

        bundle.putString("KEY","RTU MIREA")
        sleep(3)
        bundle.putString("AGE","19")
        sleep(3)
        bundle.putString("WORK","None")
        sleep(3)

        msg.data = bundle
        myLooper.handler.sendMessage(msg)
    }
}