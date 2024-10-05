package com.nickel.tallyscore.core

import android.content.Context
import java.lang.ref.WeakReference

object ContextProvider {
    private var contextReference: WeakReference<Context>? = null

    var context: Context
        get() = contextReference?.get()
            ?: throw IllegalStateException("ContextProvider should be initialized inside Application.")
        set(value) {
            contextReference = WeakReference(value)
        }
}
