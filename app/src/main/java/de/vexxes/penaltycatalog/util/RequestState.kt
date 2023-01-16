package de.vexxes.penaltycatalog.util
/*
sealed class RequestState<out T> {

    object Idle: RequestState<Nothing>()
    object Loading: RequestState<Nothing>()
    data class Success<T>(val data: T): RequestState<T>()
    data class Error(val error: Throwable): RequestState<Nothing>()

}
*/
/*
sealed class RequestState<out T> {

    object Idle: RequestState<Nothing>()
    object Loading: RequestState<Nothing>()
    object Success: RequestState<Nothing>()
    data class Error(val error: Throwable): RequestState<Nothing>()

}*/

sealed class RequestState {
    object Idle: RequestState()
    object Loading: RequestState()
    object Success: RequestState()
    object Error: RequestState()
}
