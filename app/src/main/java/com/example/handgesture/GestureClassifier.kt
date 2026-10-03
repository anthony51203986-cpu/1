package com.example.handgesture

import com.google.mediapipe.tasks.components.containers.NormalizedLandmark
import kotlin.math.sqrt

object GestureClassifier {

        enum class Gesture {
                    OPEN_PALM,
                            FIST,
                                    PINCH,
                                            THUMBS_UP,
                                                    UNKNOWN
        }

            private const val PINCH_THRESHOLD = 0.06f

                fun classify(landmarks: List<NormalizedLandmark>): Gesture {
                            if (landmarks.size < 21) return Gesture.UNKNOWN

                                    val thumbTip = landmarks[4]
                                            val indexTip = landmarks[8]

                                                    val pinchDistance = distance(thumbTip, indexTip)
                                                            if (pinchDistance < PINCH_THRESHOLD) {
                                                                            return Gesture.PINCH
                                                            }

                                                                    val indexExtended = isFingerExtended(landmarks, 8, 6)
                                                                            val middleExtended = isFingerExtended(landmarks, 12, 10)
                                                                                    val ringExtended = isFingerExtended(landmarks, 16, 14)
                                                                                            val pinkyExtended = isFingerExtended(landmarks, 20, 18)
                                                                                                    val thumbExtended = isThumbExtended(landmarks)

                                                                                                            return when {
                                                                                                                            indexExtended && middleExtended && ringExtended && pinkyExtended ->
                                                                                                                                            Gesture.OPEN_PALM

                                                                                                                                                        thumbExtended && !indexExtended && !middleExtended && !ringExtended && !pinkyExtended &&
                                                                                                                                                                        isThumbPointingUp(landmarks) ->
                                                                                                                                                                                        Gesture.THUMBS_UP

                                                                                                                                                                                                    !indexExtended && !middleExtended && !ringExtended && !pinkyExtended && !thumbExtended ->
                                                                                                                                                                                                                    Gesture.FIST

                                                                                                                                                                                                                                else -> Gesture.UNKNOWN
                                                                                                            }
                }

                    private fun isFingerExtended(landmarks: List<NormalizedLandmark>, tipIdx: Int, pipIdx: Int): Boolean {
                                val wrist = landmarks[0]
                                        val tip = landmarks[tipIdx]
                                                val pip = landmarks[pipIdx]
                                                        return distance(tip, wrist) > distance(pip, wrist) * 1.1f
                    }

                        private fun isThumbExtended(landmarks: List<NormalizedLandmark>): Boolean {
                                    val thumbTip = landmarks[4]
                                            val thumbMcp = landmarks[2]
                                                    val pinkyMcp = landmarks[17]
                                                            return distance(thumbTip, pinkyMcp) > distance(thumbMcp, pinkyMcp) * 1.2f
                        }

                            private fun isThumbPointingUp(landmarks: List<NormalizedLandmark>): Boolean {
                                        val wrist = landmarks[0]
                                                val thumbTip = landmarks[4]
                                                        return thumbTip.y() < wrist.y() - 0.1f
                            }

                                private fun distance(a: NormalizedLandmark, b: NormalizedLandmark): Float {
                                            val dx = a.x() - b.x()
                                                    val dy = a.y() - b.y()
                                                            val dz = a.z() - b.z()
                                                                    return sqrt(dx * dx + dy * dy + dz * dz)
                                }
}
                                                            }
        }
}