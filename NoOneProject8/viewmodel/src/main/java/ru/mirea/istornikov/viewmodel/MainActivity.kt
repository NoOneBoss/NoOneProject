package ru.mirea.istornikov.viewmodel

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        val viewModel = ViewModelProvider(this).get(ProgressViewModel::class.java)

        ProgressViewModel.progressState.observe(this,
            Observer<Boolean?> { isVisibleProgressBar ->
                if (isVisibleProgressBar!!) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            })
        viewModel.showProgress()
    }
}