package com.example.beije.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.beije.R
import com.example.beije.databinding.DetailScreenBinding
import com.example.beije.databinding.ToolbarWithTitleBinding
import com.example.beije.response.Content
import com.example.beije.ui.MainActivity
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val TIME_LOADING_PDF = 1000L
const val FULLY_PROGRESSBAR = 100
const val TIME_TRANSITION_FRAGMENT = 100L

class DetailScreen : Fragment() {

    private val gson: Gson by inject()
    private lateinit var binding: DetailScreenBinding
    private lateinit var contentObject: Content
    private lateinit var toolbarBinding: ToolbarWithTitleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DetailScreenBinding.inflate(inflater, container, false)
        toolbarBinding = binding.mainToolbar

        showDetail()
        return binding.root
    }

    private fun showDetail() {
        val args = DetailScreenArgs.fromBundle(requireArguments())

        contentObject = gson.fromJson(args.content, Content::class.java)
        binding.titleDetailObject.text = contentObject.mediaTitleCustom
        binding.dataDetailObject.text = convertStringToData(contentObject.mediaDate.dateString)

        loadPreviewPdf()
        binding.showPdf.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(contentObject.mediaUrl))
            startActivity(intent)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadPreviewPdf() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()

        //
        //I tried to load the preview of the document in pdf, but sometimes it fails to display, this is a problem of loading pdf in a web page
        binding.webView.postDelayed(
            { binding.webView.loadUrl(resources.getString(R.string.google_drive_url) + contentObject.mediaUrl) },
            TIME_LOADING_PDF
        )

        binding.webView.webChromeClient =  object: WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress < FULLY_PROGRESSBAR) {
                    binding.progressBarMain.visibility = View.VISIBLE
                }
                if (newProgress == FULLY_PROGRESSBAR) {
                    binding.progressBarMain.visibility = View.GONE
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.slide_right)
        postponeEnterTransition(TIME_TRANSITION_FRAGMENT, TimeUnit.MILLISECONDS)
        setupToolbar()
    }

    private fun setupToolbar() {
        (activity as MainActivity?)?.supportActionBar?.hide()
        toolbarBinding.toolbarMain.title = getString(R.string.object_detail)
        toolbarBinding.toolbarMain.setTitleTextColor(ContextCompat.getColor(this@DetailScreen.requireContext(), R.color.white))
        toolbarBinding.toolbarMain.setBackgroundColor(ContextCompat.getColor(this@DetailScreen.requireContext(), R.color.grey))
    }

    fun convertStringToData(dataString: String, locale: Locale = Locale.getDefault()): String {
        val inputFormatter: DateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)//Mon, 27 Jul 2020 00:00:00 +0000
        val date = inputFormatter.parse(dataString)

        val outputFormatter: DateFormat = SimpleDateFormat("EEE, dd MMM yyyy", locale)
        return outputFormatter.format(date ?: "")
    }
}
