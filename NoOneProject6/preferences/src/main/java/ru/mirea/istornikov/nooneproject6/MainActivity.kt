package ru.mirea.istornikov.nooneproject6

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var editText: EditText? = null
    private var textView: TextView? = null
    private var preferences: SharedPreferences? = null
    private val SAVED_TEXT = "saved_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editView)
        textView = findViewById(R.id.textView)
        preferences = getPreferences(MODE_PRIVATE)
    }

    fun OnSaveText(view: View?) {
        val editor = preferences!!.edit()
        editor.putString(SAVED_TEXT, editText!!.text.toString())
        editor.apply()
        Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show()
    }

    fun onLoadText(view: View?) {
        val text = preferences!!.getString(SAVED_TEXT, "empty")
        textView!!.text = text
    }
}