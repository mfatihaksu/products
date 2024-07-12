package com.mfa.util

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedSendChannelException

private suspend fun <E> Channel<E>.sendOrNothing(e: E) {
    try {
        this.send(e)
    } catch (closedException: ClosedSendChannelException) {
        closedException.printStackTrace()
    }
}