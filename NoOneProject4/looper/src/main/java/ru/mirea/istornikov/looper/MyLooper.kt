package ru.mirea.istornikov.looper

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

class MyLooper : Thread() {

    lateinit var handler: Handler

    @SuppressLint("HandlerLeak")
    override fun run(){
        Log.d("MyLooper","Run")
        Looper.prepare()
        handler = object : Handler(){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Log.d("MyLooper", "${msg.data.getString("KEY")}")
                sleep(msg.data.getString("AGE")?.toLong()!!)
                Log.d("Аge","${msg.data.getString("AGE")}")
                Log.d("Work", "${msg.data.getString("WORK")}")
            }
        }
        Looper.loop()
    }
}