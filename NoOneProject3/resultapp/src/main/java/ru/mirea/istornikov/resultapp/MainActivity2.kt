package ru.mirea.istornikov.resultapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity2 : AppCompatActivity() {

    private var universityEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        universityEditText = findViewById(R.id.editTextUniversity)
    }

    fun sendResultOnMainActivityOnClick(view: View){
        val intent = Intent()
        intent.putExtra("name", universityEditText?.text.toString())
        setResult(RESULT_OK,intent)
        finish()
    }
}