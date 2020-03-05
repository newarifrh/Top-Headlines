package dev.blank.topheadline.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WebviewViewModelFactory(private val mApplication: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WebviewViewModel(mApplication) as T
    }
}