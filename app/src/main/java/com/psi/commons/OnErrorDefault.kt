package com.psi.commons

import android.util.Log

val DEFAULT_ON_ERROR: (Throwable) -> Unit = {
    // Log.e("ERROR", it.localizedMessage ?: "Null message")
}