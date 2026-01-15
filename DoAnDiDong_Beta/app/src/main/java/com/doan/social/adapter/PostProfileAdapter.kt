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
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_layout_profile, parent, false)
        return PostViewHolder(itemView)

    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        val item = postList[position]
        holder.txt_titlePost.setText(item.title)
        holder.txt_statusPost.setText("Status: "+item.status)
        holder.txt_bodyPost.setText(item.content)
        Glide.with(holder.itemView.context) //Load ảnh
            .load(item.image_url)
            .into(holder.imgv_post)
        holder.itemView.setOnClickListener {
            listener.onClickPostItem(position)
        }
    }

    override fun getItemCount(): Int = postList.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_titlePost = itemView.findViewById<TextView>(R.id.txt_titlePost)
        val imgv_post = itemView.findViewById<ImageView>(R.id.imgv_post)
        val txt_bodyPost = itemView.findViewById<TextView>(R.id.txt_bodyPost)
        val txt_statusPost = itemView.findViewById<TextView>(R.id.txt_statusPost)

    }

    interface OnClickPostItem{
        fun onClickPostItem(post: Int)
    }

}