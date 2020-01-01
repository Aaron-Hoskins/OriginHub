package com.tobysoft.originhub.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tobysoft.originhub.datasource.remote.api.GitHubApiService
import com.tobysoft.originhub.model.github.UserInformationResult
import com.tobysoft.originhub.model.github.UserRepositoryResults
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RepositoryViewModel(application: Application, val userInformationResult: UserInformationResult)
    : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val cacheFile = application.cacheDir
    val userName = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()
    val joinDate = MutableLiveData<String>()
    val userLocation = MutableLiveData<String>()
    val followersCount = MutableLiveData<String>()
    val followingCount = MutableLiveData<String>()
    val userAvatar = MutableLiveData<String>()
    val userBio = MutableLiveData<String>()
    val allRepository = MutableLiveData<List<UserRepositoryResults>>()
    val filteredRepositoryList = MutableLiveData<List<UserRepositoryResults>>()
    init {
        userName.postValue(userInformationResult.login)
        userEmail.postValue(userInformationResult.email?: "No Email Provided")
        joinDate.postValue(userInformationResult.created_at)
        userLocation.postValue(userInformationResult.location?: "No Location Provided")
        followersCount.postValue("${userInformationResult.followers} Followers")
        followingCount.postValue("Following ${userInformationResult.following}")
        userBio.postValue(userInformationResult.bio?: "No Bio Provided")
        userAvatar.postValue(userInformationResult.avatar_url)
        getUserRepositories()
    }

    fun onRepositoryFilteringTextChanged(currentInput: CharSequence,start: Int,before : Int,
                                         count :Int){
        val filterString = currentInput.toString()
        val filteredList
                = allRepository.value?.filter {it.name.startsWith(filterString)}
        filteredRepositoryList.postValue(filteredList)
    }

    fun getUserRepositories() {
       compositeDisposable.add(GitHubApiService
            .gitGitHubApiService(cacheFile)
            .getRepositories(userInformationResult.login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { userRepositoryResults -> allRepository.postValue(userRepositoryResults)} )
    }
}