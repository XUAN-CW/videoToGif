package com.xuanchengwei.videotogif

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.arthenica.mobileffmpeg.FFmpeg
import com.xuanchengwei.videotogif.ui.theme.VideoToGifTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideoToGifTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}
@Composable
fun MyComposable() {
    // Load OpenCV library
    val opencvLoaded = remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        opencvLoaded.value = org.opencv.android.OpenCVLoader
    }

    if (opencvLoaded.value) {
        // Execute FFmpeg commands
        val command = arrayOf("-i", "input.mp4", "output.mp4")
        val result = FFmpeg.execute(command)

        // Check the result
        if (result == FFmpeg.RETURN_CODE_SUCCESS) {
            // FFmpeg command executed successfully
            // Handle the output video here
        } else {
            // FFmpeg command failed
            // Handle the error here
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VideoToGifTheme {
        Greeting("Android")
    }
}