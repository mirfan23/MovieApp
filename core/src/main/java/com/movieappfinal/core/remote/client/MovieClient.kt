package com.movieappfinal.core.remote.client

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.movieappfinal.core.remote.interceptor.MovieInterceptor
import com.movieappfinal.core.remote.services.ApiEndPoint
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
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(I::class.java)
    }
}