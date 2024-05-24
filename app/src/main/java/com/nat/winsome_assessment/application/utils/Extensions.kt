package com.nat.winsome_assessment.application.utils

import android.annotation.SuppressLint


/**
 * This function to extract the hours and minutes from the returned movie length number*/
fun Int.toHoursAndMinutes(): String {
    val hours = this / 60
    val minutes = this % 60
    return if (hours > 0) {
        val minutesText = if (minutes == 0) "" else " $minutes mins"
        "$hours hour$minutesText"
    } else {
        "$minutes mins"
    }
}

/**
 * This to only extract the first digit instead of the all digits after the number*/
@SuppressLint("DefaultLocale")
fun Double.toVote(): String {
    return String.format("%.1f", this)
}