package com.example.sofittasktranformer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sofittasktranformer.db.DataRepo
import com.example.sofittasktranformer.utils.showLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

/**
 * @author Umer Bilal
 * Created 8/7/2022 at 3:20 PM
 */

open class BaseViewModel(private var dataRepo: DataRepo) : ViewModel() {

    protected suspend fun getToken() =  dataRepo.getOrRequestNewBearerToken()


}