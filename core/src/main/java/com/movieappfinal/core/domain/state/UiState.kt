package com.movieappfinal.core.domain.state

sealed class UiState<out R> {
    object  Loading : UiState<Nothing>()
    data class Success<out T>(val data: T): UiState<T>()
    data class Error(val error: Throwable): UiState<Nothing>()
    object Empty : UiState<Nothing>()
}

fun <T> UiState<T>.onSuccess(
    execute: (data: T) -> Unit
): UiState<T> = apply {
    if (this is UiState.Success) {
        println("TagUIState" + data.toString())
        execute(data)
    }
}

fun <T> UiState<T>.oError(
    execute: (error: Throwable) -> Unit
): UiState<T> = apply {
    if (this is UiState.Error) {
        println("TagUIState" + error.message)
        execute(error)
    }
}

fun <T> UiState<T>.onLoading(
    execute: () -> Unit
): UiState<T> = apply {
    if (this is UiState.Loading) {
        println("TagUIState Loading")
        execute()
    }
}