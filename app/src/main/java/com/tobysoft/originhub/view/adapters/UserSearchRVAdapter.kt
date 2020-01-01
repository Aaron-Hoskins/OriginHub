package com.tobysoft.originhub.view.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tobysoft.originhub.R
import com.tobysoft.originhub.datasource.local.USER_INFO_ID
import com.tobysoft.originhub.model.github.UserInformationResult
import com.tobysoft.originhub.view.activities.RepositoryActivity
import kotlinx.android.synthetic.main.user_search_item.view.*

class UserSearchRVAdapter()
    : RecyclerView.Adapter<UserSearchRVAdapter.ViewHolder>() {
    private var userInformationList = ArrayList<UserInformationResult>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.user_search_item, parent, false))

    override fun getItemCount() = userInformationList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bindUserInfo(userInformationList[position])

    fun updateUserInformationList(userInformation : UserInformationResult) {
        userInformationList.add(userInformation)
        notifyDataSetChanged()
    }

    fun clearUserInformationList() {
        userInformationList.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bindUserInfo(userInformation : UserInformationResult){
            itemView.tvUserName.text = userInformation.login
            itemView.tvRepositoryCount.text = "Repo: ${userInformation.public_repos}"
            Glide
                .with(itemView)
                .load(userInformation.avatar_url)
                .into(itemView.imgUserAvatar)
            itemView.setOnClickListener {
                val intent = Intent(it.context, RepositoryActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable(USER_INFO_ID, userInformation)
                intent.putExtras(bundle)
                it.context.startActivity(intent)
            }
        }
    }
}