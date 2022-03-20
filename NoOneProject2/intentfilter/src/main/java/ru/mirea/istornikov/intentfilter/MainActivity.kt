package ru.mirea.istornikov.intentfilter

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    fun openBrowser(view : View){
        val url = Uri.parse("https://www.mirea.ru/")
        val intent = Intent(Intent.ACTION_VIEW, url)

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent)
        } else {
            Log.d("Intent", "Не получается обработать намерение!")
        }
    }

    fun openWithExtra(view : View){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "MIREA")
        intent.putExtra(Intent.EXTRA_TEXT,"Istornikov Andrey Mixaylovich")
        startActivity(Intent.createChooser(intent,"Мое фио"))

    }
}