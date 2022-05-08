package ru.mirea.istornikov.mireaproject.ui.browser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import ru.mirea.istornikov.mireaproject.R
import ru.mirea.istornikov.mireaproject.databinding.FragmentBrowserBinding


class BrowserFragment : Fragment() {

    private lateinit var binding : FragmentBrowserBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentBrowserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val webView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://yandex.ru")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}