package com.example.aplikasigithubusernavigationdanapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasigithubusernavigationdanapi.R
import com.example.aplikasigithubusernavigationdanapi.data.Followers

class FollowersAdapter(private val listReview: List<Followers>) :
    RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_follower, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.name.text = listReview[position].login
        viewHolder.username.text = listReview[position].htmlUrl
        viewHolder.organisasiflwrs.text = listReview[position].organizationsUrl
        Glide.with(viewHolder.itemView.context)
            .load(listReview[position].avatarUrl)
            .circleCrop()
            .into(viewHolder.image)
    }

    override fun getItemCount() = listReview.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_nama_listfollower)
        val username: TextView = view.findViewById(R.id.tv_list_username_follower)
        val organisasiflwrs: TextView = view.findViewById(R.id.tvflwer_organizations)
        val image: ImageView = view.findViewById(R.id.img_list_follower)
    }
}

