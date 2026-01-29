package com.doan.social.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doan.social.R
import com.doan.social.model.PostModel

class PostProfileAdapter(private val postList: MutableList<PostModel>, private val listener: OnClickPostItem) : RecyclerView.Adapter<PostProfileAdapter.PostViewHolder>() {
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
        Glide.with(holder.itemView.context)
            .load(item.user?.avatarurl)
            .placeholder(R.drawable.avartar_profile)
            .error(R.drawable.avartar_profile)
            .into(holder.imgProfile)
        holder.itemView.setOnClickListener {
            listener.onClickPostItem(item.id)
        }
    }

    override fun getItemCount(): Int = postList.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        var txtContent = itemView.findViewById<TextView>(R.id.txtCommentContent)
        var txtName = itemView.findViewById<TextView>(R.id.txtCommentName)
        var imgProfile = itemView.findViewById<ImageView>(R.id.imgCommentProfile)
    }

    interface OnClickPostItem{
        fun onClickPostItem(post: Int)
    }

}