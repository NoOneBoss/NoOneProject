package ru.mirea.istornikov.resultapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var textViewResult: TextView? = null
    final val REQUEST_CODE: Int = 143

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewResult = findViewById(R.id.dateResultText)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null){
            val university: String? = data.getStringExtra("name")
            setUniversityTextView(university)
        }
    }

    fun startDataCatching(view: View){
        val intent = Intent(this, MainActivity2::class.java)
        startActivityForResult(intent,REQUEST_CODE)
    }

    private fun setUniversityTextView(university: String?) {
        textViewResult?.text = university
    }
}