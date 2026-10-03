package com.example.handgesture

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.core.Delegate
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult

class HandLandmarkerHelper(
        private val context: Context,
            private val listener: LandmarkerListener
) {

        private var handLandmarker: HandLandmarker? = null

            interface LandmarkerListener {
                        fun onResults(result: HandLandmarkerResult, inputWidth: Int, inputHeight: Int)
                                fun onError(error: String)
            }

                init {
                            setupHandLandmarker()
                }

                    private fun setupHandLandmarker() {
                                try {
                                                val baseOptionsBuilder = BaseOptions.builder()
                                                                .setModelAssetPath("hand_landmarker.task")
                                                                                .setDelegate(Delegate.CPU)

                                                                                            val options = HandLandmarker.HandLandmarkerOptions.builder()
                                                                                                            .setBaseOptions(baseOptionsBuilder.build())
                                                                                                                            .setMinHandDetectionConfidence(0.5f)
                                                                                                                                            .setMinTrackingConfidence(0.5f)
                                                                                                                                                            .setMinHandPresenceConfidence(0.5f)
                                                                                                                                                                            .setNumHands(1)
                                                                                                                                                                                            .setRunningMode(RunningMode.LIVE_STREAM)
                                                                                                                                                                                                            .setResultListener(::returnLivestreamResult)
                                                                                                                                                                                                                            .setErrorListener(::returnLivestreamError)
                                                                                                                                                                                                                                            .build()

                                                                                                                                                                                                                                                        handLandmarker = HandLandmarker.createFromOptions(context, options)
                                } catch (e: Exception) {
                                                listener.onError("Failed to initialize HandLandmarker: ${e.message}")
                                                            Log.e(TAG, "HandLandmarker init error", e)
                                }
                    }

                        fun detectAsync(bitmap: Bitmap, timestampMs: Long) {
                                    val mpImage = BitmapImageBuilder(bitmap).build()
                                            handLandmarker?.detectAsync(mpImage, timestampMs)
                        }

                            private fun returnLivestreamResult(result: HandLandmarkerResult, input: com.google.mediapipe.framework.image.MPImage) {
                                        listener.onResults(result, input.width, input.height)
                            }

                                private fun returnLivestreamError(error: RuntimeException) {
                                            listener.onError(error.message ?: "Unknown HandLandmarker error")
                                                    Log.e(TAG, "HandLandmarker runtime error", error)
                                }

                                    fun close() {
                                                handLandmarker?.close()
                                                        handLandmarker = null
                                    }

                                        companion object {
                                                    private const val TAG = "HandLandmarkerHelper"
                                        }
}
                                }
            }
}
)