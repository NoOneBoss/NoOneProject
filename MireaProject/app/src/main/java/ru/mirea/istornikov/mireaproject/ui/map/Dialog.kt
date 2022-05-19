package ru.mirea.istornikov.mireaproject.ui.map

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.yandex.mapkit.geometry.Point
import ru.mirea.istornikov.mireaproject.R

class Dialog(val title : String, val message: String, val point : Point) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
            .setMessage(message)
            .setIcon(R.drawable.ic_location)
            .setNeutralButton("Закрыть", DialogInterface.OnClickListener() { _, _ ->
                run {
                    dialog?.cancel()
                }
            })
            /*.setPositiveButton("Посмотреть панораму", DialogInterface.OnClickListener() { _, _ ->
                run {
                    startActivity(Intent(requireContext(), PanoramaActivity::class.java))
                    dialog?.cancel()
                }
            })*/
        return builder.create()
    }
}