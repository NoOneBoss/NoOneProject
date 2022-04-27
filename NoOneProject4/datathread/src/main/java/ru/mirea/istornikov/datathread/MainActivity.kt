package ru.mirea.istornikov.datathread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var tvInfo = findViewById<TextView>(R.id.textView)
        val run1 = Runnable { tvInfo.text = "run1" }
        val run2 = Runnable { tvInfo.text = "run2" }
        val run3 = Runnable { tvInfo.text = "run3" }

        val t = Thread(Runnable {
            try {
                TimeUnit.SECONDS.sleep(2)
                runOnUiThread(run1)
                TimeUnit.SECONDS.sleep(1)
                tvInfo.postDelayed(run3,2000)
                tvInfo.post(run2)
            } catch(e:InterruptedException){
                e.printStackTrace()
            }
        })
        t.start()
    }
}