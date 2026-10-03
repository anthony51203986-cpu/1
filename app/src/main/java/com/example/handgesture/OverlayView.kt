package com.example.handgesture

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mediapipe.tasks.components.containers.NormalizedLandmark

private val HAND_CONNECTIONS = listOf(
        0 to 1, 1 to 2, 2 to 3, 3 to 4,
            0 to 5, 5 to 6, 6 to 7, 7 to 8,
                5 to 9, 9 to 10, 10 to 11, 11 to 12,
                    9 to 13, 13 to 14, 14 to 15, 15 to 16,
                        13 to 17, 17 to 18, 18 to 19, 19 to 20,
                            0 to 17
)

@Composable
fun OverlayView(
        landmarks: List<NormalizedLandmark>?,
            gestureName: String,
                modifier: Modifier = Modifier
) {
        Box(modifier = modifier.fillMaxSize()) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                                    if (landmarks != null) {
                                                        val points = landmarks.map { lm ->
                                                                            Offset(lm.x() * size.width, lm.y() * size.height)
                                                                                            }

                                                                                                            HAND_CONNECTIONS.forEach { (startIdx, endIdx) ->
                                                                                                                                if (startIdx < points.size && endIdx < points.size) {
                                                                                                                                                            drawLine(
                                                                                                                                                                                            color = Color.Green,
                                                                                                                                                                                                                        start = points[startIdx],
                                                                                                                                                                                                                                                    end = points[endIdx],
                                                                                                                                                                                                                                                                                strokeWidth = 4f
                                                                                                                                                            )
                                                                                                                                }
                                                                                                            }

                                                                                                                            points.forEach { point ->
                                                                                                                                                drawCircle(color = Color.Yellow, radius = 8f, center = point)
                                                                                                                            }
                                    }
                    }

                            Text(
                                            text = gestureName,
                                                        color = Color.White,
                                                                    fontSize = 28.sp,
                                                                                fontWeight = FontWeight.Bold,
                                                                                            modifier = Modifier
                                                                                                            .align(Alignment.TopCenter)
                                                                                                                            .padding(top = 48.dp)
                            )
        }
}
                                                                                                                                }}
                                    }
                    }
        }
}
)