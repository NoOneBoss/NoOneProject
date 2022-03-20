package ru.mirea.istornikov.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class Dialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Здравствуй мирэа")
            .setMessage("Успех близок?")
            .setIcon(R.mipmap.ic_launcher_round)
            .setPositiveButton("Иду дальше", DialogInterface.OnClickListener() { _, _ ->
                run {
                    (activity as MainActivity).onOkClicked()
                    dialog?.cancel()
                }
            })
            .setNeutralButton("На паузе", DialogInterface.OnClickListener() { _,_ ->
                run {
                    (activity as MainActivity).onNeutralClicked()
                    dialog?.cancel()
                }
            })
            .setNegativeButton("Нет",DialogInterface.OnClickListener() { _,_ ->
                run {
                    (activity as MainActivity).onCancelClicked()
                    dialog?.cancel()
                }
            })
        return builder.create()
    }
}