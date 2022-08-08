package com.example.sofittasktranformer.ui.fragmens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


abstract class BaseFragment<T : ViewDataBinding>(private var layoutId: Int) : Fragment() {
    lateinit var binding: T
    val isBindingInit by lazy { this::binding.isInitialized }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, null, false)
handleBackPress()
        return binding.root
    }

    private fun handleBackPress() {
        // This callback will only be called when MyFragment is at least Started.
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(false /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    if(findNavController().navigateUp()){

                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context ?: return
    }

    abstract fun getProgressBar():ProgressBar

     fun showProgressDialog(){
        getProgressBar().visibility=View.VISIBLE
    }
     fun hideProgressDialog(){
        getProgressBar().visibility=View.GONE
    }

}