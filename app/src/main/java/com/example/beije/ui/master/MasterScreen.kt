package com.example.beije.ui.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beije.MainActivity
import com.example.beije.R
import com.example.beije.databinding.MasterScreenBinding
import com.example.beije.response.Content
import com.example.beije.response.MonclairObjectResponse
import com.example.beije.utils.Navigator
import com.example.beije.utils.exhaustive
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.master_screen.*
import org.koin.android.ext.android.inject
import timber.log.Timber

class MasterScreen : Fragment() {

    private val masterViewModel: MasterViewModel by inject()
    private val navigator: Navigator by inject()
    private lateinit var binding: MasterScreenBinding
    private lateinit var toolbarBinding: androidx.appcompat.widget.Toolbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = MasterScreenBinding.inflate(inflater, container, false)
        toolbarBinding = binding.mainToolbar
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        masterViewModel.send(MasterEvent.LoadData)
        observeMasterViewModel()
    }

    private fun setupToolbar() {
        (activity as MainActivity?)?.supportActionBar?.hide()
        toolbarBinding.title = "Title Toolbar"
    }

    private fun observeMasterViewModel() {
        masterViewModel.observe(lifecycleScope) {
            when (it) {
                is MasterState.InProgress -> showProgressBar()
                is MasterState.LoadedData -> showTitleObject(it.data)
                is MasterState.Error -> showError(it.error.message.toString())
            }.exhaustive
        }
    }

    private fun showTitleObject(titleObject: MonclairObjectResponse) {
        val contentList = titleObject.content
        binding.recyclerNewsList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MasterScreen.requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = MasterAdapter(contentList, navigator)
        }
    }

    private fun showProgressBar() {
        binding.progressBarMain.visibility = View.VISIBLE
    }

    private fun showError(error: String) {
        binding.progressBarMain.visibility = View.GONE
        Timber.w("error during house creation: $error")
        showSnackBarRetry()
    }

    private fun showSnackBarRetry() {
        Snackbar.make(binding.mainConstraintLayout, resources.getString(R.string.network_error), Snackbar.LENGTH_INDEFINITE)
            .setAction(resources.getString(R.string.retry)) {
                masterViewModel.send(MasterEvent.LoadData)
            }
            .show()
    }
}
