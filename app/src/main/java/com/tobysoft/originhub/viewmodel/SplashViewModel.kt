package com.tobysoft.originhub.viewmodel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tobysoft.originhub.view.activities.MainActivity
import com.tobysoft.originhub.view.activities.SplashActivity
import java.util.Timer
import kotlin.concurrent.schedule


const val delayTime :Long = 3000
class SplashViewModel : ViewModel() {
    val startMainActivity = MutableLiveData<Boolean>()
    var isReady = true
    init {
        Timer().schedule(delayTime) {
            startMainActivity.postValue(isReady)
        }
    }
}