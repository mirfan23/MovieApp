package com.movieappfinal.core.remote.interceptor

import com.movieappfinal.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class MovieInterceptor() : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = BuildConfig.Access_Token
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder()
            .addHeader("accept", "application/json")
            .header("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(request)
    }
}