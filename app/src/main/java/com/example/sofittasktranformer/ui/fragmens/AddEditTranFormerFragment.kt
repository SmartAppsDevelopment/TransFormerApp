package com.example.sofittasktranformer.ui.fragmens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.sofittasktranformer.R
import com.example.sofittasktranformer.databinding.FragmentEditBinding
import com.example.sofittasktranformer.model.TranFormerDataModel
import com.example.sofittasktranformer.utils.getSelectedText
import com.example.sofittasktranformer.utils.getSelectedTextInt
import com.example.sofittasktranformer.utils.showToasts
import com.example.sofittasktranformer.viewmodel.EditFragmentViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddEditTranFormerFragment : BaseFragment<FragmentEditBinding>(R.layout.fragment_edit) {

    private val viewModel by viewModels<EditFragmentViewModel>()
    private val incomingData: AddEditTranFormerFragmentArgs by navArgs()


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataToAdapter()

        if (incomingData.dataModel != null) {
            ///////Handling Case When For Edit or Update
            binding.ivAvatarimg.visibility = View.VISIBLE
            binding.btndel.text = "Update"
            with(incomingData.dataModel!!) {
                val teamInt = if (team.equals("A")) 0 else 1
                binding.edtvName.setText(name)
                setDefaultParam(
                    teamInt,
                    strength - 1,
                    intelligence - 1,
                    speed - 1,
                    endurance - 1,
                    rank - 1,
                    courage - 1, firepower - 1, skill - 1
                )
                Picasso.get().load(teamIcon).error(R.drawable.ic_baseline_error_outline_24)
                    .into(binding.ivAvatarimg)


            }
            binding.btndel.setOnClickListener {
                val currentData = getParametersFromUi()
                currentData.id=incomingData.dataModel!!.id
                viewModel.updateData(currentData)
            }
        } else {
            setDefaultParam()
            binding.ivAvatarimg.visibility = View.GONE
            binding.btndel.text = "Add"
            binding.btndel.setOnClickListener {
                val currentData = getParametersFromUi()
                viewModel.addData(currentData)
            }
        }
        addObserver()
    }

    private fun addObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.transformerData.collect {
                when (it) {
                    is EditFragmentViewModel.ViewUiStates.Error -> {
                        showToasts(it.message ?: "Unknown Error")
                    }
                    is EditFragmentViewModel.ViewUiStates.Idle -> {
                        hideProgressDialog()
                    }
                    is EditFragmentViewModel.ViewUiStates.Loading -> {
                        showProgressDialog()
                    }
                    is EditFragmentViewModel.ViewUiStates.SuccessAdd -> {
                        hideProgressDialog()
                        showToasts("Data Added Successfully")
                    }
                    is EditFragmentViewModel.ViewUiStates.SuccessUpdate -> {
                        hideProgressDialog()
                        showToasts("Data Updated Successfully")
                    }
                }
            }
        }
    }

    private fun getAdapterForteam(): ArrayAdapter<String> {
        val languages = resources.getStringArray(R.array.teamrank)
        return ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, languages
        )
    }

    private fun getAdapterForNumbers(): ArrayAdapter<String> {
        val languages = resources.getStringArray(R.array.ranknumber)
        return ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, languages
        )
    }

    @SuppressLint("SetTextI18n")
    private fun setDataToAdapter() {
        val adapterteam = getAdapterForteam().apply {

        }
        val adapter = getAdapterForNumbers()

        binding.team.spinnertv.setAdapter(adapterteam)
        binding.team.tvTitle.text = "Team"

        binding.strength.spinnertv.setAdapter(adapter)
        binding.strength.tvTitle.text = "Strength"

        binding.intelligence.spinnertv.setAdapter(adapter)
        binding.intelligence.tvTitle.text = "Intelligence"

        binding.speed.spinnertv.setAdapter(adapter)
        binding.speed.tvTitle.text = "Speed"

        binding.endurance.spinnertv.setAdapter(adapter)
        binding.endurance.tvTitle.text = "Endurance"

        binding.rank.spinnertv.setAdapter(adapter)
        binding.rank.tvTitle.text = "Rank"

        binding.courage.spinnertv.setAdapter(adapter)
        binding.courage.tvTitle.text = "Courage"

        binding.firepower.spinnertv.setAdapter(adapter)
        binding.firepower.tvTitle.text = "FirePower"

        binding.skill.spinnertv.setAdapter(adapter)
        binding.skill.tvTitle.text = "Skill"
    }

    fun setDefaultParam(
        team: Int = 1,
        strength: Int = 1,
        intelligence: Int = 1,
        speed: Int = 1,
        endurance: Int = 1,
        rank: Int = 1,
        courage: Int = 1,
        firepower: Int = 1,
        skill: Int = 1
    ) {
        binding.team.spinnertv.setSelection(team)
        binding.strength.spinnertv.setSelection(strength)
        binding.intelligence.spinnertv.setSelection(intelligence)
        binding.speed.spinnertv.setSelection(speed)
        binding.endurance.spinnertv.setSelection(endurance)
        binding.rank.spinnertv.setSelection(rank)
        binding.courage.spinnertv.setSelection(courage)
        binding.firepower.spinnertv.setSelection(firepower)
        binding.skill.spinnertv.setSelection(skill)
    }

    fun getParametersFromUi(): TranFormerDataModel {
        return with(binding) {
            TranFormerDataModel(
                team = if (team.spinnertv.getSelectedText().equals("AutoBoat")) "A" else "D",
                courage = courage.spinnertv.getSelectedTextInt(),
                strength = strength.spinnertv.getSelectedTextInt(),
                intelligence = intelligence.spinnertv.getSelectedTextInt(),
                speed = speed.spinnertv.getSelectedTextInt(),
                endurance = endurance.spinnertv.getSelectedTextInt(),
                rank = rank.spinnertv.getSelectedTextInt(),
                firepower = firepower.spinnertv.getSelectedTextInt(),
                skill = skill.spinnertv.getSelectedTextInt(),
                name = edtvName.text.toString(),
                teamIcon = "",
                id = ""
            )
        }
    }

    override fun getProgressBar(): ProgressBar = binding.progressbar
}