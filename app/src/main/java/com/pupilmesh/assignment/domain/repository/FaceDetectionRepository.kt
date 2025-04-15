package com.pupilmesh.assignment.domain.repository

import android.media.Image
import com.pupilmesh.assignment.domain.model.DetectedFace

interface FaceDetectionRepository {
    fun detectFaces(
        image: Image,
        rotationDegrees: Int,
        onFacesDetected: (List<DetectedFace>) -> Unit
    )
}