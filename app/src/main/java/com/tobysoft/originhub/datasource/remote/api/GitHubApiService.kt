package com.tobysoft.originhub.datasource.remote.api

import com.tobysoft.originhub.model.github.UserInformationResult
import com.tobysoft.originhub.model.github.UserRepositoryResults
import com.tobysoft.originhub.model.github.UserSearchResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

interface GitHubApiService {
    companion object{
        fun gitGitHubApiService(cacheFile : File) =
            RetrofitHelper.getRetrofitInstance(GITHUB_BASE_URL, cacheFile).create(GitHubApiService::class.java)
    }

    @GET("/search/users")
    fun getUserSearchResults(@Query("q")userName: String): Observable<UserSearchResult>

    @GET("/users/{user_name}")
    fun getUserInformation(@Path("user_name")userName: String) : Observable<UserInformationResult>

    @GET("/users/{user_name}/repos")
    fun getRepositories(@Path("user_name") userName: String): Observable<List<UserRepositoryResults>>
}