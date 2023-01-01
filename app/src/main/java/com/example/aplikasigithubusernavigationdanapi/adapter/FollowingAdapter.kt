package com.example.aplikasigithubusernavigationdanapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasigithubusernavigationdanapi.R
import com.example.aplikasigithubusernavigationdanapi.data.Following

class FollowingAdapter(private val listReview: List<Following>) :
    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_following, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.nameflwng.text = listReview[position].login
        viewHolder.usernameflwng.text = listReview[position].htmlUrl
        viewHolder.organizationflwng.text = listReview[position].organizationsUrl
        Glide.with(viewHolder.itemView.context)
            .load(listReview[position].avatarUrl)
            .circleCrop()
            .into(viewHolder.imageflwng)
    }

    override fun getItemCount() = listReview.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameflwng: TextView = view.findViewById(R.id.tv_nama_listfollowing)
        val usernameflwng: TextView = view.findViewById(R.id.tv_list_username_following)
        val organizationflwng: TextView = view.findViewById(R.id.tvflwng_organizations)
        val imageflwng: ImageView = view.findViewById(R.id.img_list_following)
    }
}
