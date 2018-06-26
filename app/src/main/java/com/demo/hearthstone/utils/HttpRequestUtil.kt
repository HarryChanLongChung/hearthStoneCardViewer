package com.demo.hearthstone.utils

import com.demo.hearthstone.datamodels.Models
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import java.io.IOException

class HttpRequestUtil {
    companion object {
        interface HttpRequestListener {
            fun onSuccess(response: Models.MpApiJsonResponse?)
            fun onFail(e: Exception)
        }

        fun getallCards(callback: HttpRequestListener) {
            val client = OkHttpClient()
            val request = Request.Builder()
                    .header("X-Mashape-Key", "zjhMgSjOR7mshJPHePi1XzPOir7wp1ouOF9jsn4f7GgNpyF3v8")
                    .url("https://omgvamp-hearthstone-v1.p.mashape.com/cards")
                    .build()

            val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory()).build()
            val responseAdapter = moshi.adapter(Models.MpApiJsonResponse::class.java)

            client.newCall(request).enqueue(
                    object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            callback.onFail(e)

                        }

                        @Throws(IOException::class)
                        override fun onResponse(call: Call, response: Response) {
                            if (!response.isSuccessful) callback.onFail(IOException("Unexpected code $response"))

                            callback.onSuccess(responseAdapter.fromJson(response.body()!!.source()))
                        }
                    })
        }
    }
}