package com.movieappfinal.core.remote.client

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.movieappfinal.core.BuildConfig
import com.movieappfinal.core.remote.interceptor.MovieInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieClient(
    val movieInterceptor: MovieInterceptor,
    val chuckerInterceptor: ChuckerInterceptor,
) {

    inline fun<reified I> create(): I {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(movieInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(I::class.java)
    }
}