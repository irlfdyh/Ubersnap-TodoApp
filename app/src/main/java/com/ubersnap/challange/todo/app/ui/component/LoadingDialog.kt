package com.ubersnap.challange.todo.app.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ubersnap.challange.todo.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingDialog() {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        properties = DialogProperties()
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Box(modifier = Modifier.padding(32.dp)) {
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))
                LottieAnimation(
                    composition = composition,
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.Center),
                    iterations = LottieConstants.IterateForever
                )
            }
        }
    }
}