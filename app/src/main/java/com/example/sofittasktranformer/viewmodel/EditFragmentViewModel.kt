package com.example.sofittasktranformer.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.sofittasktranformer.db.DataRepo
import com.example.sofittasktranformer.model.TranFormerDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditFragmentViewModel @Inject constructor(
    private var dataRepo: DataRepo,
    private var savedStateHandle: SavedStateHandle
) : BaseViewModel(dataRepo) {

    private val dataStateFlow =
        MutableStateFlow<ViewUiStates<TranFormerDataModel>>(
            ViewUiStates.Idle()
        )
    val transformerData = dataStateFlow.asStateFlow()

    fun updateData(dataModel: TranFormerDataModel) = viewModelScope.launch {
        dataStateFlow.emit(ViewUiStates.Loading())
        getToken().collect {
            dataRepo.updateTransFormer(it!!, dataModel).collect {
                if (it) {
                    dataStateFlow.emit(ViewUiStates.SuccessUpdate(null))
                } else {
                    dataStateFlow.emit(ViewUiStates.Error("No Data Found "))
                }
            }
        }
    }

    fun addData(dataModel: TranFormerDataModel) = viewModelScope.launch {
        dataStateFlow.emit(ViewUiStates.Loading())
        getToken().collect {
            dataRepo.addNewTransFormer(it!!, dataModel).collect {
                if (it) {
                    dataStateFlow.emit(ViewUiStates.SuccessAdd(null))
                } else {
                    dataStateFlow.emit(ViewUiStates.Error("No Data Found "))
                }
            }
        }
    }

    sealed class ViewUiStates<T>(
        val data: T? = null,
        val message: String? = null
    ) {

        class SuccessAdd<T>(data: T?) : ViewUiStates<T>(data)

        class SuccessUpdate<T>(data: T?) : ViewUiStates<T>(data)

        class Error<T>(message: String?) : ViewUiStates<T>(null, message)

        class Loading<T> : ViewUiStates<T>()

        class Idle<T> : ViewUiStates<T>()

    }

}