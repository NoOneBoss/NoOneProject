package ru.mirea.istornikov.simplefragmentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    lateinit var fragment1: Fragment
    lateinit var fragment2: Fragment
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment1 = BlankFragment()
        fragment2 = BlankFragment2()
    }

    fun onClick(view: View){
        fragmentManager = supportFragmentManager
        when (view.id) {
            R.id.btnFragment1 -> fragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment1).commit()
            else -> println(fragmentManager.fragments)
        }
    }

}