package com.example.handgesture

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mediapipe.tasks.components.containers.NormalizedLandmark
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity(), HandLandmarkerHelper.LandmarkerListener {

        private lateinit var cameraExecutor: ExecutorService
            private var handLandmarkerHelper: HandLandmarkerHelper? = null
                private var previewView: PreviewView? = null

                    private val currentLandmarks = mutableStateOf<List<NormalizedLandmark>?>(null)
                        private val currentGesture = mutableStateOf("No hand detected")

                            private val requestPermissionLauncher =
                                    registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                                                if (granted) startCamera() else currentGesture.value = "Camera permission denied"
                                                        }

                                                            override fun onCreate(savedInstanceState: Bundle?) {
                                                                        super.onCreate(savedInstanceState)

                                                                                cameraExecutor = Executors.newSingleThreadExecutor()
                                                                                        handLandmarkerHelper = HandLandmarkerHelper(context = this, listener = this)

                                                                                                setContent {
                                                                                                                Box(modifier = Modifier.fillMaxSize()) {
                                                                                                                                    CameraPreviewView()
                                                                                                                                                    OverlayView(
                                                                                                                                                                            landmarks = currentLand
                                                                                                                                                    )
                                                                                                                }
                                                                                                }
                                                            }
}