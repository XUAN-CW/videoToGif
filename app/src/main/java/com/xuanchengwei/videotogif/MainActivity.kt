package com.xuanchengwei.videotogif

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.xuanchengwei.videotogif.ui.theme.VideoToGifTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.getExternalFilesDir("test")
        val testFile = "/storage/emulated/0/Android/data/com.xuanchengwei.videotogif/files/test/360P.mp4"
        val outputPath = "/storage/emulated/0/Android/data/com.xuanchengwei.videotogif/files/test/out.gif"

        val rc = FFmpeg.execute("-i $testFile $outputPath")

        if (rc == Config.RETURN_CODE_SUCCESS) {
            Log.i("rc", "命令执行成功")
        } else if (rc == Config.RETURN_CODE_CANCEL) {
            Log.i("rc", "用户取消了命令")
        } else {
            Log.i("rc", String.format("命令执行失败, 返回值=%d", rc))
        }

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