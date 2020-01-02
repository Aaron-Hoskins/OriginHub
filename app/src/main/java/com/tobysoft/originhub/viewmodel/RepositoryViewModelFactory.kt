package com.tobysoft.originhub.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tobysoft.originhub.model.github.UserInformationResult

@Suppress("UNCHECKED_CAST")
class RepositoryViewModelFactory(val application: Application, val userInformationResult: UserInformationResult)
    :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RepositoryViewModel(application, userInformationResult) as T
    }

}