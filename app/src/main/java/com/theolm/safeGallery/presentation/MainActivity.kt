package com.theolm.safeGallery.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.FragmentActivity
import com.theolm.core.data.LockState
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.lockState.collectAsState(initial = LockState.LOCK)
            AppTheme {
                if (state == LockState.UNLOCK) {
                    HomePage()
                } else {
                    LockedPage()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        scope.launch {
            viewModel.lockApp()
        }
    }
}
