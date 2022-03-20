package ru.mirea.istornikov.nooneproject2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    companion object{
        var TAG = MainActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG,"onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG,"onStart()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG,"onRestoreInstanceState()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG,"onRestart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG,"onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG,"onPause()")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.i(TAG,"onSaveInstanceState()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG,"onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"onDestroy()")
    }
}