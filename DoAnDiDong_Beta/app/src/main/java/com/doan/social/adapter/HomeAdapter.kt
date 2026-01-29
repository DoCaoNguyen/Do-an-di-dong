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

class HomeAdapter(private val postList: MutableList<PostModel>, private val listener: OnClickPostItem) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_APP_BAR = 0
    private val VIEW_TYPE_POST_ITEM = 1

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> VIEW_TYPE_APP_BAR
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
            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_layout,parent,false)
                HomeViewHolder(itemView,listener)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is HomeViewHolder -> {
                val item = postList[position - 1]
                holder.txtTitle.text = item.title
                holder.txtContent.text = item.content
                holder.txtVote.text = item.votes_count.toString()
                holder.txtTotalComment.text = item.comments_count.toString()
                val context = holder.itemView.context
                val defaultColor = android.graphics.Color.GRAY
                holder.imgUpvote.setColorFilter(defaultColor)
                holder.imgDownvote.setColorFilter(defaultColor)

                when (item.user_vote) {
                    1 -> {
                        holder.imgUpvote.setColorFilter(android.graphics.Color.parseColor("#FF4500"))
                    }
                    -1 -> {
                        holder.imgDownvote.setColorFilter(android.graphics.Color.parseColor("#7193FF"))
                    }
                }

                holder.imgUpvote.setOnClickListener { listener.onVoteClick(item, 1) }
                holder.imgDownvote.setOnClickListener { listener.onVoteClick(item, -1) }

                holder.txtContent.post {
                    if (holder.txtContent.lineCount > 3){
                        holder.txtSeeMore.visibility = View.VISIBLE
                    } else {
                        holder.txtSeeMore.visibility = View.GONE
                    }
                }

                holder.txtName.text = item.user?.username
                Glide.with(holder.itemView.context)
                    .load(item.user?.avatarurl)
                    .placeholder(R.drawable.avartar_profile)
                    .error(R.drawable.avartar_profile)
                    .into(holder.imgProfile)
                holder.itemView.setOnClickListener {
                    listener.onClickPostItem(item)
                }


            }
            is PostBarViewHolder -> {
            }
        }
    }

    override fun getItemCount(): Int = postList.size + 1

    class AppBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class PostBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class HomeViewHolder(itemView: View, listener: HomeAdapter.OnClickPostItem) : RecyclerView.ViewHolder(itemView) {
        var txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        var txtContent = itemView.findViewById<TextView>(R.id.txtCommentContent)
        var txtName = itemView.findViewById<TextView>(R.id.txtCommentName)
        var imgProfile = itemView.findViewById<ImageView>(R.id.imgCommentProfile)
        var txtSeeMore = itemView.findViewById<TextView>(R.id.txtSeeMore)
        var imgUpvote = itemView.findViewById<ImageView>(R.id.imgbtnUpVote)
        var imgDownvote = itemView.findViewById<ImageView>(R.id.imgbtnDownVote)
        var txtVote = itemView.findViewById<TextView>(R.id.txtVote)
        var txtTotalComment = itemView.findViewById<TextView>(R.id.txtTotalComment)
    }

    interface OnClickPostItem{
        fun onClickPostItem(post: PostModel)
        fun onVoteClick(post: PostModel, voteType: Int)
    }

}