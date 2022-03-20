package ru.mirea.istornikov.dialog

import android.app.DatePickerDialog
import android.content.Context

class CustomDateDialog(context: Context,
                       listener: OnDateSetListener?,
                       year: Int,
                       month: Int,
                       dayOfMonth: Int
) : DatePickerDialog(context, listener, year, month, dayOfMonth) {
}