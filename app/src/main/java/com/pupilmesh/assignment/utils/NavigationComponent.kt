package com.pupilmesh.assignment.utils

import okio.ByteString.Companion.encode
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class NavigationComponent( val route: String ) {
    object SignInScreen : NavigationComponent("signInScreen")
    object HomeScreen : NavigationComponent("homeScreen")
    object MangaDetailScreen : NavigationComponent("mangaDetailScreen") {
        const val routeWithArgs = "mangaDetailScreen/{thumb}/{title}/{subtitle}/{summary}"
        fun createRoute(thumb: String, title: String, subtitle: String, summary: String): String {
            return "mangaDetailScreen/${thumb.encodeUrl()}/${title.encodeUrl()}/${subtitle.encodeUrl()}/${summary.encodeUrl()}"
        }
    }
    object FaceDetectionScreen : NavigationComponent("faceDetectionScreen")
}

fun String.encodeUrl(): String =
    URLEncoder.encode(this, StandardCharsets.UTF_8.toString())

fun String.decodeUrl(): String =
    URLDecoder.decode(this, StandardCharsets.UTF_8.toString())