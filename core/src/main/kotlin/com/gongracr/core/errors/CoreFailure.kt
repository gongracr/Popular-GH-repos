package com.gongracr.core.errors

sealed interface CoreFailure {

    sealed class StorageFailure : CoreFailure {
        data object DataNotFound : StorageFailure()
        data class Generic(val rootCause: Throwable) : StorageFailure()
    }

    data class NetworkFailure(val cause: Throwable?) : CoreFailure
}