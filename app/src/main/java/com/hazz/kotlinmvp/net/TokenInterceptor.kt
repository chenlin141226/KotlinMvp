package com.hazz.kotlinmvp.net

import com.google.gson.Gson
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean

import java.io.IOException
import java.nio.charset.Charset
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)
        val responseBody = response.body()

        val source = responseBody!!.source()
        source.request(java.lang.Long.MAX_VALUE)
        val buffer = source.buffer()
        var charset: Charset? = Charset.forName("utf-8")
        val contentType = responseBody.contentType()
        if (contentType != null) {
            charset = contentType.charset(charset)
        }
        val bodyString = buffer.clone().readString(charset!!)
        val bean : HomeBean = Gson().fromJson<HomeBean>(bodyString, HomeBean::class.java)
//        if (bean.getCode() === 4000) {
//            val intent = Intent(MyApplication.sInstance, DialogActivity::class.java)
//            MyApplication.sInstance.startActivity(intent)
//        }

        return response
    }
}
