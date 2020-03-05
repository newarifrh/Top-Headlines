package dev.blank.topheadline.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class WebviewViewModel(application: Application?) : AndroidViewModel(application!!) {
    var url: MutableLiveData<String?>? = MutableLiveData()
}