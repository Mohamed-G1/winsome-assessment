package com.nat.winsome_assessment.application.data.remote.network

import com.nat.winsome_assessment.application.general.GeneralState
import com.nat.winsome_assessment.application.general.defaultGeneralState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrorHandler {
    companion object {
        private val _generalState = MutableStateFlow(defaultGeneralState())
        val generalState = _generalState.asStateFlow()
        fun resetGeneralState() {
            _generalState.value = GeneralState.NoState()
        }

        fun <T> Flow<T>.handleNetworkError(
            onHandlingFinished: (() -> Unit)? = null
        ): Flow<T> = catch { throwable ->
            when (throwable) {
                is HttpException -> when (throwable.response()?.code()) {
                    400 -> {
                        _generalState.update { GeneralState.BadRequestState() }

                    }

                    404 -> {
                        _generalState.update { GeneralState.NotFoundState() }

                    }

                    500 -> {
                        _generalState.update { GeneralState.InternalServerErrorState() }
                    }

                }

                is SocketTimeoutException -> {
                    _generalState.value = GeneralState.SocketTimeoutState()
                }

                is ConnectException -> {
                    _generalState.value = GeneralState.ConnectExceptionState()

                }

                is UnknownHostException -> {
                    _generalState.value = GeneralState.UnknownHostState()
                }
            }
            onHandlingFinished?.invoke()
        }

    }

}