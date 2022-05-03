package ru.mirea.istornikov.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ProgressViewModel : ViewModel() {
    fun showProgress() {
        progressState.postValue(true)
        Handler().postDelayed({ progressState.postValue(false) }, 10000)
    }

    companion object {
        val progressState = MutableLiveData<Boolean>()
    }


}