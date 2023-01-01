package com.example.aplikasigithubusernavigationdanapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.aplikasigithubusernavigationdanapi.R
import com.example.aplikasigithubusernavigationdanapi.data.SearchResponse

class SearchAdapter(
    private val item: List<SearchResponse.ItemsItem?>?,
    private val callback: ((data: SearchResponse.ItemsItem?) -> Unit)
) : Adapter<SearchAdapter.SearchViewHolder>() {
    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.img_list_user)
        val name: TextView = itemView.findViewById(R.id.tv_nama_listuser)
        val username: TextView = itemView.findViewById(R.id.tv_list_username)
        val organization: TextView = itemView.findViewById(R.id.tv_organizations)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.name.text = item?.get(position)?.login
        holder.username.text = item?.get(position)?.url
        holder.organization.text = item?.get(position)?.organizationsUrl
        Glide.with(holder.itemView.context)
            .load(item?.get(position)?.avatarUrl)
            .circleCrop()
            .into(holder.image)

        holder.itemView.setOnClickListener {
            callback.invoke(item?.get(position))
        }
    }

    override fun getItemCount(): Int = item?.size ?: 0
}