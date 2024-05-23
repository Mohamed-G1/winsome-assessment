package com.nat.winsome_assessment.application.data.remote.network.interceptors

import com.nat.winsome_assessment.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AcceptContentInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer ${BuildConfig.TOKEN}")
                .build()
        return chain.proceed(request)
    }
}