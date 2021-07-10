package uz.abuyork.k_phone.api

import okhttp3.Interceptor
import okhttp3.Response
import uz.abuyork.k_phone.utils.PrefUtills

class AppInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        if (!PrefUtills.getToken().isNullOrEmpty()){
            original.newBuilder().addHeader("token", PrefUtills.getToken())
        }
        return chain.proceed(original)
    }

}