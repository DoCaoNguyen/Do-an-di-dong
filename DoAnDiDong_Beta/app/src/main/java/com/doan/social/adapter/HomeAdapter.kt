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

class HomeAdapter(private val postList: MutableList<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_APP_BAR = 0
    private val VIEW_TYPE_POST_BAR = 1
    private val VIEW_TYPE_POST_ITEM = 2

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> VIEW_TYPE_APP_BAR
            1 -> VIEW_TYPE_POST_BAR
            else -> VIEW_TYPE_POST_ITEM
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_APP_BAR -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.appbar_layout,parent,false)
                AppBarViewHolder(itemView)
            }
            VIEW_TYPE_POST_BAR -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.postbar_layout,parent,false)
                PostBarViewHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_layout,parent,false)
                HomeViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is HomeViewHolder -> {
                val item = postList[position - 2]
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

            }
            is PostBarViewHolder -> {
                // Initialize PostBar views if needed
            }
        }
    }

    override fun getItemCount(): Int = postList.size

    class AppBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class PostBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        var txtContent = itemView.findViewById<TextView>(R.id.txtContent)
        var txtName = itemView.findViewById<TextView>(R.id.txtName)
        var imgProfile = itemView.findViewById<ImageView>(R.id.imgProfile)
    }

}