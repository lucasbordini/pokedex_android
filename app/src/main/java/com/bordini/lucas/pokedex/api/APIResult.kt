package com.bordini.lucas.pokedex.api

sealed class APIResult<T>
data class Success<T>(val data: T): APIResult<T>()
sealed class Failure<T>(open val cause: Throwable? = null): APIResult<T>()

data class UnexpectedError<T>(override val cause: Throwable? = null) : Failure<T>(cause)