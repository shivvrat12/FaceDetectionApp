package com.pupilmesh.assignment.presentation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pupilmesh.assignment.utils.NavigationComponent

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Manga : BottomNavItem(NavigationComponent.HomeScreen.route, "Manga", Icons.Default.MenuBook)
    object FaceDetection : BottomNavItem(NavigationComponent.FaceDetectionScreen.route, "Face", Icons.Default.Face)

    companion object {
        val items = listOf(Manga, FaceDetection)
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentRoute = currentBackStackEntry?.destination?.route

    BottomNavigation {
        BottomNavItem.items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(NavigationComponent.HomeScreen.route) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}
