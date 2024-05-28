package com.nat.winsome_assessment.application.presentation.general

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun StatusBarIconColoring(showAsDarkIcons: Boolean) {
    val systemController = rememberSystemUiController()

    // we are using side effect when we have a compose state in a non compose code
    // and it will executed when a success composition occurs...
    SideEffect {
        systemController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = showAsDarkIcons
        )
    }
}