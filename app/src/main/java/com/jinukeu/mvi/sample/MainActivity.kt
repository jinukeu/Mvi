package com.jinukeu.mvi.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jinukeu.mvi.extensions.collectWithLifecycle
import com.jinukeu.mvi.extensions.findActivity
import com.jinukeu.mvi.extensions.noRippleClickable
import com.jinukeu.mvi.ui.theme.MviTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MviTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SampleRoute()
                }
            }
        }
    }
}

@Composable
fun SampleRoute(
    viewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            MainUiSideEffect.TerminateApp -> context.findActivity().finish()
        }
    }

    SampleScreen(
        uiState = uiState,
        onClickGetListButton = viewModel::getDummyList,
        onClickTerminateButton = viewModel::terminateApp,
    )
}

@Composable
fun SampleScreen(
    uiState: MainUiState = MainUiState(),
    onClickGetListButton: () -> Unit = {},
    onClickTerminateButton: () -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Button(onClick = onClickTerminateButton) {
                Text(text = "앱 종료")
            }

            Button(onClick = onClickGetListButton) {
                Text(text = "더미 리스트 불러오기")
            }

            uiState.dummyList.forEach {
                Text(text = "더미 $it")
            }
        }

        if (uiState.isLoading) {
            LoadingScreen()
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .noRippleClickable { },
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(36.dp),
            strokeWidth = 4.dp,
            color = Color.Black,
            trackColor = Color.Transparent,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MviTheme {
        SampleScreen()
    }
}