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
import com.theolm.safeGallery.presentation.ui.page.NavGraphs
import com.theolm.safeGallery.presentation.ui.page.appCurrentDestinationAsState
import com.theolm.safeGallery.presentation.ui.page.startAppDestination

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            val currentDestination = navController.appCurrentDestinationAsState().value
                ?: NavGraphs.root.startAppDestination

            BottomBar(
                currentDestination = currentDestination,
                onItemClick = {
                    navController.navigate(it) {
                        launchSingleTop = true
                    }
                }
            )
        }
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController
        )
    }
}

