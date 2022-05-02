package ru.mirea.istornikov.internalfilestorage

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var tv: TextView
    private val LOG_TAG = MainActivity::class.java.simpleName
    private val fileName = "mirea.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.textView)
        val string = "Hello mirea!"
        val outputStream: FileOutputStream
        try {
            outputStream = openFileOutput(fileName, MODE_PRIVATE)
            outputStream.write(string.toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Thread {
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            tv.post(Runnable { tv.setText(textFromFile) })
        }.start()
    }

    val textFromFile: String?
        get() {
            var inputStream: FileInputStream? = null
            try {
                inputStream = openFileInput(fileName)
                val bytes = ByteArray(inputStream.available())
                inputStream.read(bytes)
                val text = String(bytes)
                Log.d(LOG_TAG, text)
                return text
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close()
                    } catch (e: IOException) {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            return null
        }
}