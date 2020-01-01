package com.tobysoft.originhub.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tobysoft.originhub.R
import com.tobysoft.originhub.model.github.UserRepositoryResults
import kotlinx.android.synthetic.main.repository_item.view.*

class RepositoryRVAdapter(var repositoryList : List<UserRepositoryResults>)
    : RecyclerView.Adapter<RepositoryRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.repository_item, parent, false))

    override fun getItemCount() = repositoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bindRepository(repositoryList[position])

    fun updateList(updatedList : List<UserRepositoryResults>) {
        repositoryList = updatedList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindRepository(repositoryResults: UserRepositoryResults) {
            itemView.tvRepositoryName.text = repositoryResults.name
            itemView.tvRepositoryForks.text = "${repositoryResults.forks} Forks"
            itemView.tvRepositoryStars.text = "${repositoryResults.stargazers_count} Stars"
        }
    }
}