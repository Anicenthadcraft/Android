package com.ancient.ancient_handcraft.webhelper.api

import android.provider.SyncStateContract
import android.util.Log
import com.ancient.ancient_handcraft.Utils.Constants
import com.ancient.ancient_handcraft.app.AppData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiClient {
    val apiClient: Retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().connectTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES).addInterceptor(interceptor)
            .addNetworkInterceptor { chain ->
                var request: Request? = null
                val requestBuilder = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                request = requestBuilder.build()
                chain.proceed(request!!)
            }
            .build()

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    val apiCustomClient: Retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().connectTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES).addInterceptor(interceptor)
            .addNetworkInterceptor { chain ->
                var request: Request? = null
                val requestBuilder = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization","Bearer "+Constants.token)
                request = requestBuilder.build()
                chain.proceed(request!!)
            }
            .build()

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()
    }

    val apiService: ApiServices
        get() = apiClient.create(ApiServices::class.java)

    val apiCustomService: ApiServices
        get() = apiCustomClient.create(ApiServices::class.java)
}