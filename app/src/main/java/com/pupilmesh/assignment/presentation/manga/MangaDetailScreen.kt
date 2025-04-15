package com.pupilmesh.assignment.presentation.manga

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun MangaDetailScreen(
    navController: NavHostController,
    title: String,
    subtitle: String,
    summary: String,
    thumb: String
) {
    Column(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        Row (modifier = Modifier.fillMaxWidth()) {
            AsyncImage(thumb, null, modifier = Modifier.size(height = 200.dp, width = 160.dp)
                .padding(start = 20.dp), contentScale = ContentScale.FillBounds)
            Column(modifier = Modifier.padding(start = 10.dp, end = 20.dp).weight(0.5f)) {
                Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 20.dp),
                    fontSize = 16.sp)
                Text(subtitle , modifier = Modifier.padding(top = 10.dp), fontSize = 14.sp)
            }
        }
        Text(summary, modifier = Modifier.padding(20.dp), fontSize = 14.sp, letterSpacing = 1.sp, lineHeight = 20.sp)
    }
}