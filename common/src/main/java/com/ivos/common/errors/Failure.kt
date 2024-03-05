package com.ivos.common.errors

import com.ivos.common.R

interface Failure {

    fun getFailureMessage(): Int = R.string.default_error

}

class IdleError : Failure
