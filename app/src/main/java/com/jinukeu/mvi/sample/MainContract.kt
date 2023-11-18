package com.jinukeu.mvi.sample

import com.jinukeu.mvi.base.SideEffect
import com.jinukeu.mvi.base.UiState

data class MainUiState(
    val isLoading: Boolean = false,
    val dummyList: List<String> = emptyList(),
): UiState

sealed interface MainUiSideEffect: SideEffect {
    data object TerminateApp : MainUiSideEffect
}