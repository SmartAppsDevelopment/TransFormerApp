package com.example.sofittasktranformer.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.sofittasktranformer.db.DataRepo
import com.example.sofittasktranformer.model.TranFormerDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranFormerFragmentViewModel @Inject constructor(
    private var dataRepo: DataRepo,
    private var savedStateHandle: SavedStateHandle
) : BaseViewModel(dataRepo) {

    init {
        getAllDataList()
    }

    private val dataStateFlow =
        MutableStateFlow<ViewUiStates<List<TranFormerDataModel>>>(ViewUiStates.Loading())
    val transformerData = dataStateFlow.asStateFlow()

    private fun getAllDataList() = viewModelScope.launch {
        getToken().collect {
            dataRepo.getAllListOfTranFormers().collect {
                if (it.isNotEmpty()) {
                    dataStateFlow.emit(ViewUiStates.Success(it))
                } else {
                    dataStateFlow.emit(ViewUiStates.Error("No Data Found "))
                }
            }
        }
    }

    suspend fun getDataFromDataBaseById(id: String) {
        dataRepo.getAllListOfTranFormers().collect {
            val finddata = it.filter {
                it.id.equals(id)
            }
            if (finddata.isNotEmpty()) {
                dataStateFlow.emit(ViewUiStates.GetUserDataForUpdate(finddata))
            }
        }

    }

    suspend fun refreshDataFromServer() {
        getToken().collect {
            dataRepo.refreshDataFromServer(it ?: "")
        }
    }

    suspend fun deleteData(userID: String) {
        dataStateFlow.emit(
            ViewUiStates.Loading()
        )
        getToken().collect {
            dataRepo.deleteTranFormer(it ?: "", userID).collect {
                if (it)
                    dataStateFlow.emit(ViewUiStates.SuccessFullyDeletedData())
                else
                    dataStateFlow.emit(
                        ViewUiStates.Error("Some Thing Went Wrong While deleting data ")
                    )
            }
        }
    }


    sealed class ViewUiStates<T>(
        val data: T? = null,
        val message: String? = null
    ) {

        class Success<T>(data: T) : ViewUiStates<T>(data)

        class Error<T>(message: String?) : ViewUiStates<T>(null, message)

        class Loading<T> : ViewUiStates<T>()

        class SuccessFullyDeletedData<T> : ViewUiStates<T>()

        data class GetUserDataForUpdate<T>(val dataUser: T) : ViewUiStates<T>(dataUser)

    }

}