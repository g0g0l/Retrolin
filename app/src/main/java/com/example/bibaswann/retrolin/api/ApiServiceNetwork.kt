package com.example.bibaswann.retrolin.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiServiceNetwork private constructor()//hide the public constructor
    : Interceptor {

    private val logger: okhttp3.logging.HttpLoggingInterceptor
        get() {
            val logging = okhttp3.logging.HttpLoggingInterceptor()
            logging.level = okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS
            logging.level = okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
            logging.level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
            return logging
        }

    fun getNetworkService(url: String): WebServiceInterface {
        return createRetrofitInstance(url).create(WebServiceInterface::class.java)
    }

    private fun createRetrofitInstance(url: String): Retrofit {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.addInterceptor(this)
        okHttpClient.addInterceptor(logger)
        retrofit.baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient.connectTimeout(60,
                        TimeUnit.SECONDS).writeTimeout(60,
                        TimeUnit.SECONDS).readTimeout(60,
                        TimeUnit.SECONDS).build())
        return retrofit.build()
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder: Request.Builder
        builder = request.newBuilder()
        val request1 = builder.build()
        //Here we can modify request1 and response if we want (like adding header or auth)
        val response = chain.proceed(request1)

        return response
    }

    companion object {
        private val retrofit = Retrofit.Builder()

        fun getInstance(): ApiServiceNetwork {
            return ApiServiceNetwork()
        }
    }
}
