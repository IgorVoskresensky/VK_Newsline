package com.ivos.common.errors

data class ThrowableWrapper(val throwable: Throwable = Throwable()) : Failure
