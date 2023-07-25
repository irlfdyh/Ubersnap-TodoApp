package com.ubersnap.challange.todo.app.ui

sealed class LoadState<T> {
    class Loading<T> : LoadState<T>()
    class Empty<T> : LoadState<T>()
    class Failed<T> : LoadState<T>()
    data class Available<T>(val data: T) : LoadState<T>()
}