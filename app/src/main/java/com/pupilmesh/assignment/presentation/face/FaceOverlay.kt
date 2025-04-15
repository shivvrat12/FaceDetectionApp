package com.pupilmesh.assignment.presentation.face

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.pupilmesh.assignment.domain.model.DetectedFace

@Composable
fun FaceOverlay(
    faces: List<DetectedFace>,
    imageWidth: Int = 480,
    imageHeight: Int = 640
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val boxWidth = canvasWidth * 0.5f
        val boxHeight = canvasHeight * 0.3f
        drawRect(
            color = Color.Green,
            topLeft = androidx.compose.ui.geometry.Offset(
                (canvasWidth - boxWidth) / 2,
                (canvasHeight - boxHeight) / 2
            ),
            size = androidx.compose.ui.geometry.Size(boxWidth, boxHeight),
            style = Stroke(width = 4f)
        )

        faces.forEach { face ->
            val rect = face.boundingBox

            val scaleX = canvasWidth / imageWidth
            val scaleY = canvasHeight / imageHeight

            val left = canvasWidth - (rect.right * scaleX)
            val top = rect.top * scaleY
            val right = canvasWidth - (rect.left * scaleX)
            val bottom = rect.bottom * scaleY

            val faceBox = androidx.compose.ui.geometry.Rect(left, top, right, bottom)

            val refLeft = (canvasWidth - boxWidth) / 2
            val refTop = (canvasHeight - boxHeight) / 2
            val refRight = refLeft + boxWidth
            val refBottom = refTop + boxHeight
            val referenceBox = androidx.compose.ui.geometry.Rect(refLeft, refTop, refRight, refBottom)

            val isInside = faceBox.left >= referenceBox.left &&
                    faceBox.top >= referenceBox.top &&
                    faceBox.right <= referenceBox.right &&
                    faceBox.bottom <= referenceBox.bottom

            drawRect(
                color = if (isInside) Color.Green else Color.Red,
                topLeft = faceBox.topLeft,
                size = faceBox.size,
                style = Stroke(width = 4f)
            )
        }
    }
}

