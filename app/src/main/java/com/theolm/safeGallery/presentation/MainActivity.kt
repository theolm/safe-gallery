package com.theolm.safeGallery.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.ramcosta.composedestinations.DestinationsNavHost
import com.theolm.core.data.AppAuthState
import com.theolm.safeGallery.presentation.ui.page.home.NavGraphs
import com.theolm.safeGallery.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val scope = MainScope()

    @Inject
    lateinit var appAuthState: AppAuthState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        scope.launch {
            appAuthState.lock()
        }
    }
}
