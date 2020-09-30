package com.example.beije.ui.detail

import android.os.Bundle
import android.text.SpannableString
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.beije.R
import com.example.beije.databinding.DetailScreenBinding
import com.example.beije.databinding.ToolbarWithTitleBinding
import com.example.beije.response.Content
import com.example.beije.ui.MainActivity
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class DetailScreen : Fragment() {

    private val detailViewModel: DetailViewModel by inject()
    private val gson: Gson by inject()
    private lateinit var binding: DetailScreenBinding
    private lateinit var contentObject: Content
    private lateinit var toolbarBinding: ToolbarWithTitleBinding
    private lateinit var linkPdf: SpannableString

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
        binding.linkDetailObject.text = contentObject.mediaUrl
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
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
        return dataString.substring(0, 16)
    }
}
