package com.pupilmesh.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pupilmesh.assignment.presentation.face.FaceDetectionScreen
import com.pupilmesh.assignment.presentation.manga.MangaDetailScreen
import com.pupilmesh.assignment.presentation.manga.MangaScreen
import com.pupilmesh.assignment.presentation.signin.SignInScreen
import com.pupilmesh.assignment.presentation.signin.SignInViewModel
import com.pupilmesh.assignment.ui.theme.AssignmentTheme
import com.pupilmesh.assignment.utils.NavigationComponent
import com.pupilmesh.assignment.utils.decodeUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssignmentTheme {
                assignNavigation()
            }
        }
    }
}

@Composable
fun assignNavigation(
     signInViewModel: SignInViewModel = hiltViewModel()
){
    val navController = rememberNavController()
    val isLoggedIn by signInViewModel.isLoggedIn
    val startDestination = if (isLoggedIn) NavigationComponent.HomeScreen.route else NavigationComponent.SignInScreen.route
    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavigationComponent.SignInScreen.route) {
            SignInScreen(loggedIn = {
                navController.navigate(NavigationComponent.HomeScreen.route){
                    popUpTo(0)
                }
            })
        }
        composable(NavigationComponent.HomeScreen.route) {
            MangaScreen(navController = navController)
        }
        composable(
            route = NavigationComponent.MangaDetailScreen.routeWithArgs,
            arguments = listOf(
                navArgument("thumb") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
                navArgument("subtitle") { type = NavType.StringType },
                navArgument("summary") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            MangaDetailScreen(
                navController = navController,
                thumb = backStackEntry.arguments?.getString("thumb")?.decodeUrl() ?: "",
                title = backStackEntry.arguments?.getString("title")?.decodeUrl() ?: "",
                subtitle = backStackEntry.arguments?.getString("subtitle")?.decodeUrl() ?: "",
                summary = backStackEntry.arguments?.getString("summary")?.decodeUrl() ?: ""
            )
        }
        composable(NavigationComponent.FaceDetectionScreen.route) {
            FaceDetectionScreen( navController = navController )
        }
    }
}