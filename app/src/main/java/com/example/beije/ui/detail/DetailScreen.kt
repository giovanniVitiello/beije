package com.example.beije.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding.linkDetailObject.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(contentObject.mediaUrl))
            startActivity(intent)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadPreviewPdf() {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.postDelayed(
            { binding.webView.loadUrl(resources.getString(R.string.google_drive_url) + contentObject.mediaUrl) },
            500
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.slide_right)
        postponeEnterTransition(250, TimeUnit.MILLISECONDS)
        setupToolbar()
    }

    private fun setupToolbar() {
        (activity as MainActivity?)?.supportActionBar?.hide()
        toolbarBinding.toolbarMain.title = getString(R.string.object_detail)
        toolbarBinding.toolbarMain.setTitleTextColor(ContextCompat.getColor(this@DetailScreen.requireContext(), R.color.white))
        toolbarBinding.toolbarMain.setBackgroundColor(ContextCompat.getColor(this@DetailScreen.requireContext(), R.color.grey))
    }

    private fun convertStringToData(dataString: String): String {
        val inputFormatter: DateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)//Mon, 27 Jul 2020 00:00:00 +0000
        val date = inputFormatter.parse(dataString)

        val outputFormatter: DateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
        return outputFormatter.format(date ?: "")
    }
}
