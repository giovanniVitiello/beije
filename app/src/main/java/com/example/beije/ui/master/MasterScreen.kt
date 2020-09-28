package com.example.beije.ui.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.beije.databinding.MasterScreenBinding
import org.koin.android.ext.android.inject

class MasterScreen : Fragment() {

    private val masterViewModel: MasterViewModel by inject()
    private lateinit var binding: MasterScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = MasterScreenBinding.inflate(inflater, container, false)

        masterViewModel.send(MasterEvent.LoadData)
        return binding.root
    }
}
