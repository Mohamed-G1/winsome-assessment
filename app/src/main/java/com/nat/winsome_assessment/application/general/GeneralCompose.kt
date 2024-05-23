package com.nat.winsome_assessment.application.general

import androidx.compose.runtime.Composable
import com.nat.winsome_assessment.application.utils.ShowToast

@Composable
fun HandleGeneralCompose(
    state: GeneralState?,
) {

    if (state != null) {
        when (state) {
            is GeneralState.NoState -> {}

            is GeneralState.NotFoundState -> {
                ShowToast("Not found API")
            }

            is GeneralState.BadRequestState -> {
                ShowToast("Bad API Request")
            }

            is GeneralState.InternalServerErrorState -> {
                ShowToast("Server Error")
            }

            is GeneralState.SocketTimeoutState -> {
                ShowToast("An unexpected error occurred")
            }

            is GeneralState.ConnectExceptionState -> {
                ShowToast("Please check your internet connection")
            }

            is GeneralState.UnknownHostState -> {
                ShowToast("An unexpected error occurred")
            }
        }
    }
}