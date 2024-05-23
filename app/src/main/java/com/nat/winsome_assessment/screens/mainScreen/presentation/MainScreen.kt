package com.nat.winsome_assessment.screens.mainScreen.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainScreen(
    state: MainState,
    navigateToDetailsScreen: (() -> Unit)? = null
) {

    Log.d("##01", state.model.totalPages.toString())
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(defaultMainState())
}