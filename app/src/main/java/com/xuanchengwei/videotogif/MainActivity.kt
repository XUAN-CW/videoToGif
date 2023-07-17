package com.xuanchengwei.videotogif

import android.graphics.Bitmap
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
import com.xuanchengwei.videotogif.ui.theme.VideoToGifTheme
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.VideoWriter
import org.opencv.videoio.Videoio
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // /storage/emulated/0/Android/data/com.xuanchengwei.videotogif/files/test/360P.mp4
        this.getExternalFilesDir("test")
        if(OpenCVLoader.initDebug()){
            Log.i("OpenCVLoader","SUCCESS")
        }else{
            Log.i("OpenCVLoader","F")

        }

        convertMp4ToGif("/storage/emulated/0/Android/data/com.xuanchengwei.videotogif/files/test/360P.mp4", "/storage/emulated/0/Android/data/com.xuanchengwei.videotogif/files/test/360P.gif")

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

fun convertMp4ToGif(mp4FilePath: String, gifFilePath: String) {
    val videoCapture = VideoCapture(mp4FilePath)
    if (!videoCapture.isOpened) {
        // Handle error opening the MP4 file
        return
    }

    val frameSize = Size(
        videoCapture.get(Videoio.CAP_PROP_FRAME_WIDTH),
        videoCapture.get(Videoio.CAP_PROP_FRAME_HEIGHT)
    )

    val fourcc = VideoWriter.fourcc('G', 'I', 'F', 'S')

    val videoWriter = VideoWriter()
    videoWriter.open(gifFilePath, fourcc, 10.0, frameSize, true)

    val frame = Mat()
    val convertedFrame = Mat()

    while (videoCapture.read(frame)) {
        // Convert the frame to the desired format (e.g., grayscale or resize if needed)
        // Here, we convert the frame to grayscale
        Imgproc.cvtColor(frame, convertedFrame, Imgproc.COLOR_BGR2GRAY)

        // Write the converted frame to the GIF
        videoWriter.write(convertedFrame)
    }

    videoCapture.release()
    videoWriter.release()
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