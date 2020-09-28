package com.example.beije.ui.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.beije.R
import com.example.beije.databinding.MasterScreenBinding

class MasterScreen : Fragment() {

    private lateinit var masterViewModel: MasterViewModel
    private lateinit var binding: MasterScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = MasterScreenBinding.inflate(inflater, container, false)

        return binding.root
    }
}
