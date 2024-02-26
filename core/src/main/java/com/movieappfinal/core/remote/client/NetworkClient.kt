//package com.movieappfinal.core.remote.client
//
//import com.chuckerteam.chucker.api.ChuckerInterceptor
//import com.example.core.BuildConfig
//import com.example.core.remote.interceptor.AuthInterceptor
//import com.example.core.remote.interceptor.SessionInterceptor
//import com.example.core.remote.interceptor.TokenInterceptor
//import com.movieappfinal.core.BuildConfig
//import com.movieappfinal.core.remote.interceptor.MovieInterceptor
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//
//class NetworkClient(
////    val authInterceptor: AuthInterceptor,
//    val sessionInterceptor: SessionInterceptor,
//    val tokenInterceptor: TokenInterceptor,
//    val chuckerInterceptor: ChuckerInterceptor,
//    val movieInterceptor: MovieInterceptor
//) {
//    inline fun <reified I> create(): I {
//        val okHttpClient =  OkHttpClient.Builder()
////            .addInterceptor(authInterceptor)
//            .addInterceptor(movieInterceptor)
//            .addInterceptor(sessionInterceptor)
//            .addInterceptor(chuckerInterceptor)
//            .authenticator(tokenInterceptor)
//            .connectTimeout(120, TimeUnit.SECONDS)
//            .readTimeout(120, TimeUnit.SECONDS)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BuildConfig.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//
//        return retrofit.create(I::class.java)
//    }
//}