package com.theolm.safeGallery.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.ramcosta.composedestinations.DestinationsNavHost
import com.theolm.safeGallery.presentation.ui.page.home.NavGraphs
import com.theolm.safeGallery.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            v.setPadding(0, 0, 0, 0)
            insets
        }

        setContent {
            AppTheme {
                Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}
