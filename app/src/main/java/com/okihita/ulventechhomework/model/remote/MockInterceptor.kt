package com.okihita.ulventechhomework.model.remote

import com.okihita.ulventechhomework.BuildConfig
import okhttp3.*

class MockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {

            val responseString = getHolidayList

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                    responseString.toByteArray()))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError("MockInterceptor should be for DEBUG mode only")
        }
    }

}

const val getHolidayList = """
[{
	
}]
"""
