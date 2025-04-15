package com.pupilmesh.assignment.presentation.face

import android.media.Image
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pupilmesh.assignment.domain.model.DetectedFace
import com.pupilmesh.assignment.domain.repository.FaceDetectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaceDetectionViewModel @Inject constructor(
    private val repository: FaceDetectionRepository
) : ViewModel() {

    private val _faces = MutableStateFlow<List<DetectedFace>>(emptyList())
    val faces: StateFlow<List<DetectedFace>> = _faces

    fun onImageAvailable(image: Image, rotation: Int) {
        repository.detectFaces(image, rotation) { result ->
            viewModelScope.launch {
                _faces.emit(result)
            }
        }
    }
}
