package br.com.kotlinhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.kotlinhub.R
import br.com.kotlinhub.data.model.Item
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_repo_list.view.*

class RepoListAdapter(
    private val repoItemListener: (String) -> Unit
) : RecyclerView.Adapter<RepoListAdapter.RepoListViewHolder>() {

    inner class RepoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        return RepoListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_repo_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        val repo = differ.currentList[position]

        holder.itemView.apply {
            tvOwnerName.text = repo.owner.login
            tvRepoName.text = repo.name
            tvRepoDescription.text = repo.description
            tvStarCount.text = repo.stargazers_count.toString()
            tvForkCount.text = repo.forks_count.toString()

            Glide
                .with(this)
                .load(repo.owner.avatar_url)
                .placeholder(R.drawable.placeholder)
                .into(ivOwnerPhoto)

            setOnClickListener { repoItemListener(repo.html_url) }
        }
    }
}