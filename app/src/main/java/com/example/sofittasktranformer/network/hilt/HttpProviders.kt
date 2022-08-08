package com.example.sofittasktranformer.network.hilt

import android.content.Context
import com.example.sofittasktranformer.db.AppLocalDatabase
import com.example.sofittasktranformer.db.DataRepo
import com.example.sofittasktranformer.db.DataRepoImpl
import com.example.sofittasktranformer.network.TransformerNetworkProvider
import com.example.sofittasktranformer.network.BaseClientBuilder
import com.example.sofittasktranformer.utils.ConstantsMembers.BASE_URL
import com.example.sofittasktranformer.utils.IDataStorePreferenceAPI
import com.example.sofittasktranformer.utils.IDataStorePreferenceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

/**
 * @author Umer Bilal
 * Created 7/23/2022 at 11:22 AM
 */
class HttpProviders {

    @Module
    @InstallIn(SingletonComponent::class)
    object ApiModule {

        @HttpLoggerInterceptorBasic
        @Singleton
        @Provides
        fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }


        @HttpLoggerInterceptorBody
        @Singleton
        @Provides
        fun providesHttpLoggingInterceptor1(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }


        @HttpLoggerInterceptorHeader
        @Singleton
        @Provides
        fun providesHttpLoggingInterceptor2(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.HEADERS }


        @Singleton
        @Provides
        fun providesOkHttpClient(
            @ApplicationContext context: Context,
            @HttpLoggerInterceptorBody httpLoggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient {
            val httpCacheDirectory = File(context.cacheDir, "offlineCacheAzaanApp")
            //10 MB
           val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
            return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }


        @Singleton
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        @Singleton
        @Provides
        fun provideApiService(retrofit: Retrofit): TransformerNetworkProvider =
            retrofit.create(TransformerNetworkProvider::class.java)


        @Singleton
        @Provides
        fun provideBaseClient(@ApplicationContext context: Context): BaseClientBuilder =
            BaseClientBuilder(context)

        @Singleton
        @Provides
        fun getDataPrefrences(@ApplicationContext context: Context): IDataStorePreferenceAPI =
            IDataStorePreferenceImpl(context)

        @Singleton
        @Provides
        fun getDataPrefrencesVal(@ApplicationContext context: Context)= IDataStorePreferenceImpl(context)


        @Singleton
        @Provides
        fun getDataRepo(localDb: AppLocalDatabase,pref:IDataStorePreferenceImpl,remotedb:TransformerNetworkProvider):DataRepo= DataRepoImpl(localDb,pref,remotedb)

    }


}