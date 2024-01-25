package com.bordini.lucas.pokedex.api

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class APICallAdapter<T>(private val type: Type): CallAdapter<T, Call<APIResult<T>>> {

    override fun responseType(): Type = type
    override fun adapt(call: Call<T>): Call<APIResult<T>> = APICall(call)

    class Factory: CallAdapter.Factory() {
        override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: retrofit2.Retrofit): CallAdapter<*, *>? {
            val rawType = getRawType(returnType)
            if (rawType != Call::class.java || returnType !is ParameterizedType) return null

            val callType = getParameterUpperBound(0, returnType)
            val rawCallType = getRawType(callType)

            if (rawCallType != APIResult::class.java || callType !is ParameterizedType) return null

            val apiResultType = getParameterUpperBound(0, callType)
            return APICallAdapter<Any>(apiResultType)
        }
    }
}