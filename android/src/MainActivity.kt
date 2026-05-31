package com.arbiradar.mobile.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Material3Theme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.arbiradar.mobile.ui.theme.ArbiradarTheme
import com.arbiradar.mobile.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArbiradarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Material3Theme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}