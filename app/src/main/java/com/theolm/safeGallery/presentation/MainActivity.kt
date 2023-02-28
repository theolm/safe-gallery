package com.theolm.safeGallery.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import com.theolm.core.data.LockState
import com.theolm.safeGallery.BuildConfig
import com.theolm.safeGallery.presentation.ui.page.home.HomePage
import com.theolm.safeGallery.presentation.ui.page.lock.LockedPage
import com.theolm.safeGallery.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val scope = MainScope()
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            v.setPadding(0, 0, 0, 0)
            insets
        }

        setContent {
            val state by viewModel.lockState.collectAsState(initial = LockState.LOCK)
            AppTheme {
                AnimatedContent(targetState = state) {
                    if (it == LockState.UNLOCK || bypassLock()) {
                        HomePage()
                    } else {
                        LockedPage()
                    }
                }
            }
        }
    }

    private fun bypassLock() : Boolean = BuildConfig.DEBUG && bypassFlag

    override fun onPause() {
        super.onPause()
        scope.launch {
            viewModel.lockApp()
        }
    }

    private companion object {
        const val bypassFlag = true
    }
}
