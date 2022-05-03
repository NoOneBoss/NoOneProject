package ru.mirea.istornikov.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*


object TimeController {
    private val data = MutableLiveData<Long>()
    val time: LiveData<Long>
        get() {
            data.value = Date().time
            return data
        }

    fun setTime() {
        data.value = Date().time
    }
}