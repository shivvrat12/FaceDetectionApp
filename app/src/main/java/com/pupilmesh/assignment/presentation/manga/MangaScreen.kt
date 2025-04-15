package com.pupilmesh.assignment.presentation.manga

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.pupilmesh.assignment.domain.model.MangaData
import com.pupilmesh.assignment.presentation.BottomBar
import com.pupilmesh.assignment.utils.NavigationComponent

@Composable
fun MangaScreen(
    navController: NavController,
    viewModel: MangaViewModel = hiltViewModel()
) {
    val lazyMangaItems = viewModel.mangaPagingData.collectAsLazyPagingItems()
    Scaffold(modifier = Modifier.fillMaxSize().systemBarsPadding(),
        bottomBar = { BottomBar(navController = navController) }
        ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize().padding(it),
            contentPadding = PaddingValues(6.dp)
        ) {
            items(lazyMangaItems.itemCount) { index ->
                val manga = lazyMangaItems[index]
                if (manga != null) {
                    MangaItem(
                        manga = manga,
                        onItemClick = {
                            navController.navigate(
                                NavigationComponent.MangaDetailScreen.createRoute(
                                    manga.thumb,
                                    manga.title,
                                    manga.sub_title,
                                    manga.summary
                                )
                            )
                        }
                    )
                }
            }

            when (lazyMangaItems.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        LoadingItem()
                    }
                }

                is LoadState.Error -> {
                    item {
                        ErrorItem(message = "Failed to load more mangas") {
                            lazyMangaItems.retry()
                        }
                    }
                }

                else -> {}
            }

            when (lazyMangaItems.loadState.refresh) {
                is LoadState.Loading -> {
                    item {
                        LoadingView(modifier = Modifier.fillMaxSize())
                    }
                }

                is LoadState.Error -> {
                    item {
                        ErrorView(message = "Failed to load mangas") {
                            lazyMangaItems.retry()
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun MangaItem(manga: MangaData, onItemClick:()-> Unit) {

    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        AsyncImage(manga.thumb, null, modifier = Modifier.padding(2.dp).size(180.dp).clickable {
            onItemClick()
        })
    }
}

@Composable
fun LoadingItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorItem(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        // Add a retry button if needed
    }
}

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}