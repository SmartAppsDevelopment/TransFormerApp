package com.example.sofittasktranformer.db


import com.example.sofittasktranformer.model.TranFormerDataModel
import com.example.sofittasktranformer.model.TranFormerListDataModel
import com.example.sofittasktranformer.model.Transformers
import com.example.sofittasktranformer.network.TransformerNetworkProvider
import com.example.sofittasktranformer.utils.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.intuit.sdp.BuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import javax.inject.Inject
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


class DataRepoImpl @Inject constructor(
    private val localDbProvider: AppLocalDatabase,
    private val preferences: IDataStorePreferenceAPI,
    private val networkDataProvider: TransformerNetworkProvider
) : DataRepo {


    override suspend fun getAllListOfTranFormers(): Flow<List<TranFormerDataModel>> {
        return localDbProvider.tranFormerDataAccess().getUserNameList()
    }

    override suspend fun refreshDataFromServer(token:String) {
        try {
           val data= networkDataProvider.getAllAvailableLists(token).string()
            val parseData= Gson().fromJson(data, Transformers::class.java)
            localDbProvider.tranFormerDataAccess().deleteAllDataFromDb()
            localDbProvider.tranFormerDataAccess().insertTransFormers(parseData.transformers)
        } catch (e: Exception) {
            showLog(e.message?:"Not Defined Message")
        }
    }

    override suspend fun deleteTranFormer(token: String,userID:String) = flow{

        try {
            val netwoekResponse=networkDataProvider.deleteDataByID(token,userID)
            if(netwoekResponse.code()==401){
                emit(false)
            }else if(netwoekResponse.code()==204) {
                localDbProvider.tranFormerDataAccess().deleteWhereIdIs(userID)
                emit(true)
            }else{
                emit(false)
            }
        } catch (e: Exception) {
            emit(false)
        }
    }

    override suspend fun addNewTransFormer(token: String, dataModel: TranFormerDataModel)=flow {
        try {
            val data = networkDataProvider.postNewData(token,dataModel)
            val parseData= Gson().fromJson(data.string(), TranFormerDataModel::class.java)
            localDbProvider.tranFormerDataAccess().insertTransFormer(parseData)
            emit(true)
        } catch (e: Exception) {
            e.message
            emit(false)
        }
    }

    override suspend fun updateTransFormer(
        token: String,
        dataModel: TranFormerDataModel
    ): Flow<Boolean> =flow{
        try {
            val data = networkDataProvider.updateData(token,dataModel)
            val parseData= Gson().fromJson(data.string(), TranFormerDataModel::class.java)
            localDbProvider.tranFormerDataAccess().updateTransFormer(parseData)
            emit(true)
        } catch (e: Exception) {
            e.message
            emit(false)
        }
    }

    override fun editTransFormer(dataModel: TranFormerDataModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getOrRequestNewBearerToken() = flow {

//        if(com.example.sofittasktranformer.BuildConfig.DEBUG){
//            preferences.putPreference(ConstantsMembers.SharedPrefKeys.BEARER_TOKEN, ConstantsMembers.DEFAULT_TOKEN)
//        }
        val prefToken = preferences.getPreference(ConstantsMembers.SharedPrefKeys.BEARER_TOKEN, "")
            .firstOrNull()
        if (prefToken.isNullOrEmpty()) {
            try {
                val requestApi = networkDataProvider.getBearerToken().string()
                if (requestApi.isNotEmpty()) {
                    emit(requestApi)
                    preferences.putPreference(
                        ConstantsMembers.SharedPrefKeys.BEARER_TOKEN,
                        "Bearer $requestApi"
                    )
                } else {
                    emit(null)
                }
            } catch (e: Exception) {
                emit(null)
            }
        } else {
            emit(prefToken)
        }
    }


}