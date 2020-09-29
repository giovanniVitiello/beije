package com.example.beije.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beije.databinding.DetailScreenBinding
import com.example.beije.response.Content
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class DetailScreen : Fragment() {

    private val detailViewModel: DetailViewModel by inject()
    private val gson: Gson by inject()
    private lateinit var binding: DetailScreenBinding
    private lateinit var contentObject: Content
//    private val args by navArgs<MasterScree>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DetailScreenBinding.inflate(inflater, container, false)

        val args = DetailScreenArgs.fromBundle(requireArguments())
        contentObject = gson.fromJson(args.content, Content::class.java)
        val l = contentObject.mediaId
        return binding.root
    }
}
