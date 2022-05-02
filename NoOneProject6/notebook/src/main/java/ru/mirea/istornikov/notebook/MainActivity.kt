package ru.mirea.istornikov.notebook

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var preferences: SharedPreferences
    private lateinit var fileNameEdit: EditText
    private lateinit var fileTextEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fileNameEdit = findViewById(R.id.fileNameEdit)
        fileTextEdit = findViewById(R.id.fileTextEdit)
        preferences = getPreferences(MODE_PRIVATE)
        if (preferences.getString("FilePath", "empty") != "empty") {
            fileNameEdit.setText(preferences.getString("FilePath", "empty"))
            fileTextEdit.setText(textFromFile)
        }
    }

    fun saveClick(view: View?) {
        val outputStream: FileOutputStream
        try {
            outputStream = openFileOutput(fileNameEdit!!.text.toString(), MODE_PRIVATE)
            outputStream.write(fileTextEdit!!.text.toString().toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val editor = preferences!!.edit()
        editor.putString("FilePath", fileNameEdit!!.text.toString())
        editor.apply()
    }

    val textFromFile: String?
        get() {
            var inputStream: FileInputStream? = null
            try {
                inputStream = openFileInput(fileNameEdit!!.text.toString())
                val bytes = ByteArray(inputStream.available())
                inputStream.read(bytes)
                return String(bytes)
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