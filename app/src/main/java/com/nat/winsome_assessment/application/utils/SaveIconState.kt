package com.nat.winsome_assessment.application.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

/**
 * This class to hold the icon sate, so we need to change the icon and its color,
 * because the icon color is different between the main screen and the details screen*/
data class SaveIconState(@DrawableRes val icon: Int, val tint: Color)
