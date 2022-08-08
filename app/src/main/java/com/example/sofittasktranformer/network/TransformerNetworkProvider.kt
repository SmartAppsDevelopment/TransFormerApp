package com.example.sofittasktranformer.network

import com.example.sofittasktranformer.model.TranFormerDataModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author Umer Bilal
 * Created 7/23/2022 at 11:48 AM
 */
interface TransformerNetworkProvider {

    @GET("allspark")
    suspend fun getBearerToken(): ResponseBody

    @GET("transformers")
    suspend fun getAllAvailableLists(@Header("Authorization") token:String): ResponseBody

    @DELETE("transformers/{userid}")
    suspend fun deleteDataByID(@Header("Authorization") token:String,@Path("userid") userid: String): Response<Void>

    @POST("transformers")
    suspend fun postNewData(@Header("Authorization") token:String,@Body requestBody: TranFormerDataModel): ResponseBody

    @PUT("transformers")
    suspend fun updateData(@Header("Authorization") token:String,@Body requestBody: TranFormerDataModel): ResponseBody

}