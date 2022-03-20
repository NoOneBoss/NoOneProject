package ru.mirea.istornikov.dialog

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun openDialog(view : View){
        val dialog = Dialog()
        dialog.show(supportFragmentManager, "mirea")
    }

    fun openDateDialog(view : View){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dateDialog = CustomDateDialog(this,DatePickerDialog.OnDateSetListener{view,year,month,day ->},year,month,day)
        dateDialog.show()
    }

    fun openProgressDialog(view : View){
        val progressDialog = CustomProgressDialog(this)
        progressDialog.setCancelable(true)
        progressDialog.setMessage("progress...")
        progressDialog.show()
    }

    fun openTimeDialog(view : View){
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timeDialog = CustomTimeDialog(this, 0, { view, hourOfDay, minute -> },hour,minute,true)
        timeDialog.show()
    }



    //dialog methods
    fun onOkClicked() {
        Toast.makeText(
            applicationContext, "Вы выбрали кнопку \"Иду дальше\"!",
            Toast.LENGTH_LONG
        ).show()
    }

    fun onCancelClicked() {
        Toast.makeText(
            applicationContext, "Вы выбрали кнопку \"Нет\"!",
            Toast.LENGTH_LONG
        ).show()
    }

    fun onNeutralClicked() {
        Toast.makeText(
            applicationContext, "Вы выбрали кнопку \"На паузе\"!",
            Toast.LENGTH_LONG
        ).show()
    }
}