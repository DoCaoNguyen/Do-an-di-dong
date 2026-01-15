package com.doan.social.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doan.social.R
import com.doan.social.model.Post

class PostProfileAdapter(private val postList: MutableList<Post>, private val listener: OnClickPostItem) : RecyclerView.Adapter<PostProfileAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_layout, parent, false)
        return PostViewHolder(itemView)

    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        val item = postList[position]
        holder.txtTitle.text = item.title
        holder.txtContent.text = item.content
        holder.txtName.text = item.user?.username
        if (item.user?.imgUrl != null) {
            Glide.with(holder.itemView.context)
                .load(item.user.imgUrl)
                .into(holder.imgProfile)
        } else {
            Glide.with(holder.itemView.context)
                .load(R.drawable.avartar_profile)
                .into(holder.imgProfile)
        }
        holder.itemView.setOnClickListener {
            listener.onClickPostItem(item.id)
        }
    }

    override fun getItemCount(): Int = postList.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        var txtContent = itemView.findViewById<TextView>(R.id.txtContent)
        var txtName = itemView.findViewById<TextView>(R.id.txtName)
        var imgProfile = itemView.findViewById<ImageView>(R.id.imgProfile)
    }

    interface OnClickPostItem{
        fun onClickPostItem(post: Int)
    }

}