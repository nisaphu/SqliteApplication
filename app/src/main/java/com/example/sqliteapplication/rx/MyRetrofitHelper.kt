package com.example.sqliteapplication.rx

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class MyRetrofitHelper {

    companion object {
        fun getClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS).level =
                HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder().addInterceptor {
                val r = it.request()
                    .newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Cache-Control", "no-cache")
                    .build()
                it.proceed(r)
            }
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
        }

        private fun getInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://ec2-13-229-51-122.ap-southeast-1.compute.amazonaws.com:3000/v3.0.0/")
                .client(getClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
        }

        fun ApiInstance(): Api {
            return getInstance().create(Api::class.java)
        }
    }
}