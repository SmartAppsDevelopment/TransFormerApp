package com.example.sofittasktranformer.ui.fragmens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.sofittasktranformer.R
import com.example.sofittasktranformer.adapter.TranformerListFragmentAdapter
import com.example.sofittasktranformer.databinding.TranformerListFragmentMainBinding
import com.example.sofittasktranformer.utils.showToast
import com.example.sofittasktranformer.viewmodel.TranFormerFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainTranFormerListFragment :
    BaseFragment<TranformerListFragmentMainBinding>(R.layout.tranformer_list_fragment_main) {
    private val viewModel by viewModels<TranFormerFragmentViewModel>()

    private var adapter = TranformerListFragmentAdapter({ dataModel ->
        //Delete click
        lifecycleScope.launch {
            viewModel.deleteData(dataModel.id)
        }
    }, { dataModel ->
        //Edit Click
        lifecycleScope.launch {
            viewModel.getDataFromDataBaseById(dataModel.id)
        }
    })

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvListitems.adapter = adapter
        viewModel.viewModelScope.launch {
            viewModel.refreshDataFromServer()
        }
        binding.btnAddnew.setOnClickListener {
            val dir = MainTranFormerListFragmentDirections.actionMainFragmentToEditFragment()
            dir.dataModel = null
            findNavController().navigate(dir)
        }
        loadData()
    }

    private fun loadData() {

        viewModel.viewModelScope.launch {
            viewModel.transformerData.collect {

                when (it) {
                    is TranFormerFragmentViewModel.ViewUiStates.Error -> {
                        hideProgressDialog()
                        requireContext().showToast(it.message ?: "Unknown Error")
                    }
                    is TranFormerFragmentViewModel.ViewUiStates.Loading -> {
                        showProgressDialog()
                    }
                    is TranFormerFragmentViewModel.ViewUiStates.Success -> {
                        hideProgressDialog()
                        adapter.submitList(it.data)
                    }
                    is TranFormerFragmentViewModel.ViewUiStates.SuccessFullyDeletedData -> {
                        requireContext().showToast("Data Delete successfully")
                    }
                    is TranFormerFragmentViewModel.ViewUiStates.GetUserDataForUpdate -> {
                        val dir =
                            MainTranFormerListFragmentDirections.actionMainFragmentToEditFragment()
                        dir.dataModel = it.data!!.first()
                        findNavController().navigate(dir)
                    }
                }
                updateUIState()
            }
        }
    }

    fun updateUIState() {
        binding.isData = adapter.currentList.size > 0
    }

    override fun getProgressBar(): ProgressBar = binding.progressbar


}