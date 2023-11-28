package com.enciyo.shared

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


inline fun <T, O> Flow<Result<T>>.onMapSuccess(crossinline transform: suspend (value: T) -> O) =
    map { it.map { transform(it) } }