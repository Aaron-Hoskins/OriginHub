package com.tobysoft.originhub.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tobysoft.originhub.R
import com.tobysoft.originhub.databinding.ActivitySplashBinding
import com.tobysoft.originhub.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivitySplashBinding
    lateinit var viewModel : SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewBinding.viewmodel = viewModel
        viewModel.startMainActivity.observe(this, Observer { isReady -> continueToMainActivity()})

    }

    fun continueToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
