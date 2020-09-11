package com.utair.domain.exceptions

class NoNetworkException(
        message: String?,
        cause: Throwable? = null
) : RuntimeException(message, cause)