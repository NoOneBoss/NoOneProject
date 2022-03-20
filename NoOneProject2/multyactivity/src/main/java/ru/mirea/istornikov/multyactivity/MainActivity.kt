package ru.mirea.istornikov.multyactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickIntent(view : View){
        val name = findViewById<EditText>(R.id.inputName).text.toString()

        val intent = Intent(this, MainActivity2::class.java)
        intent.putExtra("name",name)
        startActivity(intent)
    }

    fun onClickBundle(view : View){
        val intent = Intent(this, FragmentActivity::class.java)
        startActivity(intent)
    }
}