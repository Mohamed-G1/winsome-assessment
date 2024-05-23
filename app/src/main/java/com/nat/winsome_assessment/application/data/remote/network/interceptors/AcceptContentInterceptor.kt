package com.nat.winsome_assessment.application.data.remote.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AcceptContentInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request =
            chain.request().newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NTllMDU0Mjc4NTcyZDNmY2M0MDM2NWViZTk3OWQ1MCIsInN1YiI6IjYwYjQ4Zjg0M2UwOWYzMDA0MDc2N2NjNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.wS5ohtOC6izPKqysV19DAKuGrlNyQfpY3Lzj7u-xHw0")
                .build()
        return chain.proceed(request)
    }
}