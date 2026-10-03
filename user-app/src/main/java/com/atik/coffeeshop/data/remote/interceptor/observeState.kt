package com.atik.coffeeshop.data.remote.interceptor

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.atik.coffeeshop.core.APIResponse
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> StateFlow<APIResponse<T>>.observeState(
    owner: LifecycleOwner,
    onIdle: () -> Unit = {},
    onLoading: () -> Unit = {},
    onSuccess: (T) -> Unit,
    onError: (String, Throwable?) -> Unit = { _, _ -> }
) {
    this.flowWithLifecycle(owner.lifecycle, Lifecycle.State.STARTED).onEach { state ->
        when (state) {
            is APIResponse.Idle -> onIdle()
            is APIResponse.Loading -> onLoading()
            is APIResponse.Success -> onSuccess(state.data)
            is APIResponse.Error -> onError(state.message, state.exception)
        }
    }.launchIn(owner.lifecycleScope)
}