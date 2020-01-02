package com.tobysoft.originhub.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tobysoft.originhub.R
import com.tobysoft.originhub.databinding.ActivityRepositoryBinding
import com.tobysoft.originhub.datasource.local.USER_INFO_ID
import com.tobysoft.originhub.model.github.UserRepositoryResults
import com.tobysoft.originhub.view.adapters.RepositoryRVAdapter
import com.tobysoft.originhub.viewmodel.RepositoryViewModel
import com.tobysoft.originhub.viewmodel.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.activity_repository.*

class RepositoryActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityRepositoryBinding
    lateinit var viewModelFactory : RepositoryViewModelFactory
    lateinit var viewModel : RepositoryViewModel
    lateinit var adapter : RepositoryRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_repository)
        viewModelFactory = RepositoryViewModelFactory(application, intent.extras?.getParcelable(USER_INFO_ID)!!)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RepositoryViewModel::class.java)
        viewBinding.viewmodel = viewModel
        viewModel.userAvatar.observe(this, Observer {avatar_url ->
            Glide
                .with(this)
                .load(avatar_url)
                .into(imgUserAvatar)
        })
        viewModel.allRepository.observe(this, Observer { repoList -> updateRepositoryList(repoList) })
        viewModel.filteredRepositoryList.observe(this, Observer { repoList -> updateRepositoryList(repoList) })
    }

    private fun updateRepositoryList(userRepoList : List<UserRepositoryResults>) {
        if(::adapter.isInitialized.not()) {
            val linearManager = LinearLayoutManager(this)
            adapter = RepositoryRVAdapter(userRepoList)
            rvUserRepositoryList.layoutManager = linearManager
            rvUserRepositoryList.adapter = adapter
        } else {
            adapter.updateList(userRepoList)
        }
    }
}
