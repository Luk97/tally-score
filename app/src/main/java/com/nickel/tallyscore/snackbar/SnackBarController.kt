package com.nickel.tallyscore.snackbar

import com.nickel.tallyscore.snackbar.SnackBarEvent.SnackBarAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object SnackBarController {

    private val _events = Channel<SnackBarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(
        message: String,
        actionLabel: String? = null,
        action: suspend () -> Unit = {}
    ) {
        val event = SnackBarEvent(
            message = message,
            action = actionLabel?.let {
                SnackBarAction(
                    label = it,
                    action = action
                )
            }
        )
        _events.send(event)
    }
}