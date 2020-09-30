package com.example.beije.ui.master

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beije.R
import com.example.beije.databinding.MasterScreenBinding
import com.example.beije.databinding.ToolbarWithTitleBinding
import com.example.beije.response.Content
import com.example.beije.response.MonclairObjectResponse
import com.example.beije.ui.MainActivity
import com.example.beije.utils.SwipeToDeleteCallback
import com.example.beije.utils.exhaustive
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import timber.log.Timber


class MasterScreen : Fragment() {

    private val masterViewModel: MasterViewModel by inject()
    private val gson: Gson by inject()
    private lateinit var binding: MasterScreenBinding
    private lateinit var toolbarBinding: ToolbarWithTitleBinding

    private val newsItemListener = MasterAdapter.OnClickListener { content ->
        val bundle = gson.toJson(content)
        val direction: NavDirections = MasterScreenDirections.actionNavigationMasterToNavigationDetail(bundle)
        findNavController().navigate(direction)
    }

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

        binding.pullToRefresh.setOnRefreshListener {
            binding.recyclerDataList.adapter = MasterAdapter(mutableListOf(), newsItemListener)
            masterViewModel.send(MasterEvent.LoadData)
            binding.pullToRefresh.isRefreshing = false
        }
    }

    private fun setupToolbar() {
        (activity as MainActivity?)?.supportActionBar?.hide()
        toolbarBinding.toolbarMain.title = getString(R.string.object_title)
        toolbarBinding.toolbarMain.setTitleTextColor(ContextCompat.getColor(this@MasterScreen.requireContext(), R.color.dark_blue))
        toolbarBinding.toolbarMain.setBackgroundColor(ContextCompat.getColor(this@MasterScreen.requireContext(), R.color.yellow))
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
        binding.progressBarMain.visibility = View.GONE
        val contentList = titleObject.content.toMutableList()
        binding.recyclerDataList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MasterScreen.requireContext())
            adapter = MasterAdapter(contentList, newsItemListener)
        }

        swipeToDelete(contentList)
    }

    private fun swipeToDelete(contentList: MutableList<Content>) {
        val swipeHandler = object : SwipeToDeleteCallback(this@MasterScreen.requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                contentList.removeAt(viewHolder.adapterPosition)
                binding.recyclerDataList.adapter?.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerDataList)
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
