package com.tobysoft.originhub.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tobysoft.originhub.datasource.remote.api.GitHubApiService
import com.tobysoft.originhub.model.github.Item
import com.tobysoft.originhub.model.github.UserInformationResult
import com.tobysoft.originhub.model.github.UserRepositoryResults
import com.tobysoft.originhub.model.github.UserSearchResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UsersViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    val userToAdd = MutableLiveData<UserInformationResult>()
    val clearList = MutableLiveData<Boolean>()
    private val cacheFile = application.cacheDir


    fun onUserNameTextChanged(currentInput: CharSequence,start: Int,before : Int,
                              count :Int){
        val currentUserNameEntered = currentInput.toString()
        clearList.postValue(true)
        findUsersInfo(currentUserNameEntered)
    }

    private fun findUsersInfo(userName: String) {
        compositeDisposable.add(
            getUserSearchObservable(userName)
                .subscribeOn(Schedulers.io())
                .flatMap { item -> getUserInfo(item)}
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    private fun getUserSearchObservable(userName: String) =
        GitHubApiService
            .gitGitHubApiService(cacheFile)
            .getUserSearchResults(userName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { users -> Observable.fromIterable(users.items).take(10) }

    private fun getUserInfo(item: Item) =
        GitHubApiService
            .gitGitHubApiService(cacheFile)
            .getUserInformation(item.login)
            .map { user -> userToAdd.postValue(user) }
            .subscribeOn(Schedulers.io())
}