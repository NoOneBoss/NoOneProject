package ru.mirea.istornikov.mireaproject.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import androidx.fragment.app.Fragment
import ru.mirea.istornikov.mireaproject.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        checkBox = binding.checkBox
        stars = binding.ratingBar
        preferences = requireActivity().getSharedPreferences("SETTINGS",Context.MODE_PRIVATE)

        checkBox.setOnClickListener(this::updateCheckBox)
        stars.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putFloat("stars", stars.rating)
                editor.apply()
                binding.textView3.text = "Your rating - ${stars.rating}"
            }

        checkBox.isChecked = getRememberState()
        stars.rating = getStars()

        binding.textView3.text = "Your rating - ${stars.rating}"
        return root
    }

    private lateinit var checkBox: CheckBox
    private lateinit var stars: RatingBar
    private lateinit var preferences: SharedPreferences

    fun updateCheckBox(view: View){
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean("remember", checkBox.isChecked)
        editor.apply()
    }

    fun getRememberState() : Boolean{
        return preferences.getBoolean("remember", false)
    }

    fun getStars() : Float{
        return preferences.getFloat("stars", 0f)
    }

}