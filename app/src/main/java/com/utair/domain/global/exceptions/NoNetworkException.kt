package com.utair.domain.global.exceptions

class NoNetworkException(
        message: String?,
        cause: Throwable? = null
) : RuntimeException(message, cause)