package com.tobysoft.originhub.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tobysoft.originhub.R
import com.tobysoft.originhub.databinding.ActivityMainBinding
import com.tobysoft.originhub.model.github.UserInformationResult
import com.tobysoft.originhub.view.adapters.UserSearchRVAdapter
import com.tobysoft.originhub.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityMainBinding
    lateinit var viewModel : UsersViewModel
    lateinit var userSearchRVAdapter: UserSearchRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        viewBinding.viewmodel = viewModel
        viewModel.clearList.observe(this, Observer { needsCleared ->
            if(::userSearchRVAdapter.isInitialized) {
                if(needsCleared) {
                    userSearchRVAdapter.clearUserInformationList()
                }
            }
        })
        viewModel.userToAdd.observe(this, Observer { userInformation -> addUserToList(userInformation) })
    }

    private fun addUserToList(userInformationResult: UserInformationResult) {
        if(::userSearchRVAdapter.isInitialized.not()) {
            val layoutManager = LinearLayoutManager(this)
            userSearchRVAdapter = UserSearchRVAdapter()
            rvUserSearchResultList.layoutManager = layoutManager
            rvUserSearchResultList.adapter = userSearchRVAdapter

        }
        userSearchRVAdapter.updateUserInformationList(userInformationResult)
    }
}
