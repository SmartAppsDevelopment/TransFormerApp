package com.example.sofittasktranformer.db

import com.example.sofittasktranformer.model.TranFormerDataModel
import kotlinx.coroutines.flow.Flow

interface DataRepo {


    suspend fun refreshDataFromServer(token:String)

    suspend fun getAllListOfTranFormers(): Flow<List<TranFormerDataModel>>

    suspend fun deleteTranFormer(token:String,userID:String):Flow<Boolean>

    suspend fun addNewTransFormer(token: String,dataModel: TranFormerDataModel): Flow<Boolean>

    suspend fun updateTransFormer(token: String,dataModel: TranFormerDataModel): Flow<Boolean>

    fun editTransFormer(dataModel: TranFormerDataModel)

    suspend fun getOrRequestNewBearerToken():Flow<String?>
}