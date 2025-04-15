package com.pupilmesh.assignment.presentation.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pupilmesh.assignment.domain.model.MangaData
import com.pupilmesh.assignment.domain.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val mangaRepository: MangaRepository
) : ViewModel() {

    private val pageSize = 10

    val mangaPagingData: Flow<PagingData<MangaData>> = mangaRepository.getPaginatedMangas(pageSize)
        .cachedIn(viewModelScope)
}
