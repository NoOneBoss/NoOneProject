package ru.mirea.istornikov.mireaproject.ui.browser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.mirea.istornikov.mireaproject.databinding.FragmentBrowserBinding


class BrowserFragment : Fragment() {

    private lateinit var binding : FragmentBrowserBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentBrowserBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.buttonFind.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.google.com/search?q=" + binding.editTextBrowser.getText().toString())
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}