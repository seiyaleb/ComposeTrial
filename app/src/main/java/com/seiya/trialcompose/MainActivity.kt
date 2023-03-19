package com.seiya.trialcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import com.seiya.trialcompose.compose.NavigationTop
import com.seiya.trialcompose.ui.theme.TrialComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrialComposeTheme {
                //MyApp(modifier = Modifier.fillMaxSize())
                NavigationTop(modifier = Modifier.fillMaxSize())
            }
        }
    }
}