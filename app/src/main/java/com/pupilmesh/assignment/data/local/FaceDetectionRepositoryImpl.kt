package com.pupilmesh.assignment.data.local

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector
import com.pupilmesh.assignment.domain.model.DetectedFace
import com.pupilmesh.assignment.domain.repository.FaceDetectionRepository
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FaceDetectionRepositoryImpl @Inject constructor(
    private val context: Context
) : FaceDetectionRepository {

    private val detector: FaceDetector
    private var resultCallback: ((List<DetectedFace>) -> Unit)? = null

    init {
        val options = FaceDetector.FaceDetectorOptions.builder()
            .setBaseOptions(
                com.google.mediapipe.tasks.core.BaseOptions.builder()
                    .setModelAssetPath("face_detection_short_range.tflite")
                    .build()
            )
            .setRunningMode(RunningMode.LIVE_STREAM)
            .setMinDetectionConfidence(0.5f)
            .setResultListener(::onResults)
            .setErrorListener(::onError)
            .build()

        detector = FaceDetector.createFromOptions(context, options)
    }

    private fun onResults(
        result: com.google.mediapipe.tasks.vision.facedetector.FaceDetectorResult,
        input: MPImage
    ) {
        val faces = result.detections().map {
            val boxF = it.boundingBox()
            DetectedFace(Rect(
                boxF.left.toInt(),
                boxF.top.toInt(),
                boxF.right.toInt(),
                boxF.bottom.toInt()
            ))
        }
        resultCallback?.invoke(faces)
    }

    private fun onError(error: RuntimeException) {
        resultCallback?.invoke(emptyList())
    }


    override fun detectFaces(
        image: Image,
        rotationDegrees: Int,
        onFacesDetected: (List<DetectedFace>) -> Unit
    ) {
        resultCallback = onFacesDetected

        val bitmap = imageToBitmap(image)
        val rotatedBitmap = rotateBitmap(bitmap, rotationDegrees)
        val mpImage = BitmapImageBuilder(rotatedBitmap).build()
        val timestamp = System.currentTimeMillis()

        detector.detectAsync(mpImage, timestamp)
    }

    fun imageToBitmap(image: Image): Bitmap {
        val yBuffer = image.planes[0].buffer
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, image.width, image.height), 100, out)
        val yuv = out.toByteArray()
        return BitmapFactory.decodeByteArray(yuv, 0, yuv.size)
    }
    fun rotateBitmap(bitmap: Bitmap, rotationDegrees: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(rotationDegrees.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
