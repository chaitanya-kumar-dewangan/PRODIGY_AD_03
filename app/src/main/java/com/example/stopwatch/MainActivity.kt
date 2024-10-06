package com.example.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stopwatch.ui.theme.StopwatchTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopwatchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StopwatchScreen()
                }
            }
        }
    }
}

@Composable
fun StopwatchScreen() {
    var timeInSeconds by remember { mutableStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }

    // Coroutine for updating time when the stopwatch is running
    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (isActive) {
                delay(1000L)
                timeInSeconds += 1
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatTime(timeInSeconds),
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                if (isRunning) {
                    isRunning = false
                } else {
                    isRunning = true
                }
            }) {
                Text(text = if (isRunning) "Pause" else "Start")
            }

            Button(onClick = {
                timeInSeconds = 0L
                isRunning = false
            }) {
                Text(text = "Reset")
            }
        }
    }
}

@Composable
fun formatTime(timeInSeconds: Long): String {
    val hours = timeInSeconds / 3600
    val minutes = (timeInSeconds % 3600) / 60
    val seconds = timeInSeconds % 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StopwatchTheme {
        StopwatchScreen()
    }
}
