package com.jinukeu.mvi.sample

import androidx.lifecycle.viewModelScope
import com.jinukeu.mvi.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainUiState, MainUiSideEffect>(
    MainUiState()
) {

    fun getDummyList() = viewModelScope.launch {
        intent { copy(isLoading = true) }
        delay(2000L)
        intent {
            copy(
                isLoading = false,
                dummyList = List(10) { "$it" }
            )
        }
    }

    fun terminateApp() = postSideEffect(MainUiSideEffect.TerminateApp)
}