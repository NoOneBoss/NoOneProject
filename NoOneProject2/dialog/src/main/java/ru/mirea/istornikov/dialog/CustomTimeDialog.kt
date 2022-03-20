package ru.mirea.istornikov.dialog

import android.app.TimePickerDialog
import android.content.Context

class CustomTimeDialog(
    context: Context?,
    themeResId: Int,
    listener: OnTimeSetListener?,
    hourOfDay: Int,
    minute: Int,
    is24HourView: Boolean
) : TimePickerDialog(context, themeResId, listener, hourOfDay, minute, is24HourView) {
}