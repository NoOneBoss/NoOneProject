package ru.mirea.istornikov.nooneproject7

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.istornikov.nooneproject7.MainActivity.Companion.timeView
import ru.mirea.istornikov.nooneproject7.SocketUtils.getReader
import java.io.IOException
import java.net.Socket


class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var timeView: TextView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timeView = findViewById(R.id.timeView)

    }

    fun getTimeClick(view: View?) {
        val timeTask = GetTimeTask()
        timeTask.execute()
    }
}

private class GetTimeTask : AsyncTask<Void?, Void?, String>() {
    override fun doInBackground(vararg p0: Void?): String? {
        val TAG = MainActivity::class.java.simpleName
        val host = "time-b.nist.gov"
        val port = 13
        var timeResult = ""
        try {
            val socket = Socket(host, port)
            val reader = getReader(socket)
            reader.readLine()
            timeResult = reader.readLine()
            Log.d(TAG, timeResult)
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return timeResult
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        timeView.text = result
    }
}