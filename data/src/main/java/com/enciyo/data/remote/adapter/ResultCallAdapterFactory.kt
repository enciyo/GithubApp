package com.enciyo.data.remote.adapter

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory constructor(private val coroutineScope: CoroutineScope) :
    CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        when (getRawType(returnType)) {
            Call::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                val rawType = getRawType(callType)
                if (rawType != Result::class.java) {
                    return null
                }

                val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                val paramType = getRawType(resultType)
                return ResultCallAdapter(
                    resultType = resultType,
                    paramType = paramType,
                    coroutineScope = coroutineScope,
                )
            }
            else -> return null
        }
    }

    private class ResultCallAdapter(
        private val resultType: Type,
        private val paramType: Type,
        private val coroutineScope: CoroutineScope,
    ) : CallAdapter<Type, Call<Result<Type?>>> {

        override fun responseType(): Type = resultType

        override fun adapt(call: Call<Type>): Call<Result<Type?>> {
            return ResultCall(call, paramType, coroutineScope)
        }

        private class ResultCall<T : Any>(
            private val proxy: Call<T>,
            private val paramType: Type,
            private val coroutineScope: CoroutineScope,
        ) : Call<Result<T?>> {

            override fun enqueue(callback: Callback<Result<T?>>) {
                coroutineScope.launch {
                    try {
                        val response = proxy.awaitResponse()
                        val result = response.toResult(paramType)
                        callback.onResponse(this@ResultCall, Response.success(result))
                    } catch (e: Exception) {
                        Log.i("MyLogger","From Remote :${e.message}")
                        val result = Result.failure<T>(e)
                        callback.onResponse(this@ResultCall, Response.success(result))
                    }
                }
            }

            override fun execute(): Response<Result<T?>> =
                runBlocking(coroutineScope.coroutineContext) {
                    val result = proxy.execute().toResult(paramType)
                    Response.success(result)
                }

            override fun clone(): Call<Result<T?>> =
                ResultCall(proxy.clone(), paramType, coroutineScope)

            override fun request(): Request = proxy.request()
            override fun timeout(): Timeout = proxy.timeout()
            override fun isExecuted(): Boolean = proxy.isExecuted
            override fun isCanceled(): Boolean = proxy.isCanceled
            override fun cancel() = proxy.cancel()

            private fun <T> Response<T>.toResult(paramType: Type): Result<T?> {
                return kotlin.runCatching {
                    if (isSuccessful) {
                        if (paramType == Unit::class.java) {
                            Unit as T
                        } else {
                            body()
                        }
                    } else {
                        throw HttpException(this)
                    }
                }
            }
        }
    }
}


