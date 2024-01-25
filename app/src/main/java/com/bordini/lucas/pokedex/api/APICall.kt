package com.bordini.lucas.pokedex.api

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

class APICall<T>(private val delegate: Call<T>) : Call<APIResult<T>> {
    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()

    override fun clone(): Call<APIResult<T>> {
        return APICall(delegate.clone())
    }

    override fun execute(): retrofit2.Response<APIResult<T>> = try {
        Response.success(coerceResponse(delegate.execute()))
    } catch (exception: Exception) {
        Response.success(coerceException(exception))
    }

    override fun enqueue(callback: Callback<APIResult<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onResponse(this@APICall, Response.success(coerceResponse(response)))
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                callback.onResponse(this@APICall, Response.success(coerceException(throwable)))
            }
        })
    }

    override fun cancel() {
        delegate.cancel()
    }

    private fun coerceResponse(response: Response<T>): APIResult<T> = try {
        when (response.code()) {
            200 -> response.body()?.let {
                Success(it)
            } ?: handleError(UnexpectedError())

            201 -> response.body()?.let {
                Success(it)
            } ?: handleError(UnexpectedError())

            202 -> response.body()?.let {
                Success(it)
            } ?: handleError(UnexpectedError())

            else -> handleError(UnexpectedError())
        }
    } catch (exception: Exception) {
        coerceException(exception)
    }

    private fun coerceException(exception: Throwable): APIResult<T> = UnexpectedError(exception)

    private fun handleError(error: UnexpectedError<T>): UnexpectedError<T> {
        return error
    }
}