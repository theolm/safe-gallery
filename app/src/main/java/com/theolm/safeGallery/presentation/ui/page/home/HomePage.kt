package com.theolm.safeGallery.presentation.ui.page.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.theolm.safeGallery.presentation.ui.page.NavGraphs
import com.theolm.safeGallery.presentation.ui.page.appCurrentDestinationAsState
import com.theolm.safeGallery.presentation.ui.page.destinations.GalleryPageDestination
import com.theolm.safeGallery.presentation.ui.page.destinations.NotesPageDestination
import com.theolm.safeGallery.presentation.ui.page.startAppDestination

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage() {
    val navController = rememberNavController()
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            val showBottomBar = when(currentDestination) {
                NotesPageDestination, GalleryPageDestination -> true
                else -> false
            }

            if (showBottomBar) {
                BottomBar(
                    currentDestination = currentDestination,
                    onItemClick = {
                        navController.navigate(it) {
                            // Pop up to the root of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(NavGraphs.root) {
                                saveState = true
                            }

                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController
        )
    }
}

