package com.example.beije.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.beije.R
import com.example.beije.databinding.DetailScreenBinding
import com.example.beije.databinding.MasterScreenBinding
import org.koin.android.ext.android.inject

class DetailScreen : Fragment() {

    private val detailViewModel: DetailViewModel by inject()
    private lateinit var binding: DetailScreenBinding

    companion object {
        fun getIntent(context: Context, id: Int) = Intent(context, DetailScreen::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DetailScreenBinding.inflate(inflater, container, false)

        return binding.root
    }
}
