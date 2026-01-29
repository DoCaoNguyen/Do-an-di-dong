package com.doan.social.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doan.social.R
import com.doan.social.model.CommentModel
import com.doan.social.model.PostModel


class PostDetailAdapter(private val post: PostModel?, private val commentModelList: MutableList<CommentModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_APP_BAR = 0
    private val VIEW_TYPE_POST_ITEM = 1
    private val VIEW_TYPE_COMMENT_BAR = 2
    private val VIEW_TYPE_COMMENT_ITEM = 3

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> VIEW_TYPE_APP_BAR
            1 -> VIEW_TYPE_POST_ITEM
            2 -> VIEW_TYPE_COMMENT_BAR
            else -> VIEW_TYPE_COMMENT_ITEM
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when(viewType){

            VIEW_TYPE_APP_BAR -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.appbar_layout, parent, false)
                AppBarViewHolder(itemView)
            }

            VIEW_TYPE_POST_ITEM -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_layout,parent,false)
                PostItemViewHolder(itemView)
            }

            VIEW_TYPE_COMMENT_BAR -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.commentbar_layout,parent,false)
                CommentBarViewHolder(itemView)
            }

            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comment_layout,parent,false)
                CommentItemViewHolder(itemView)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is PostItemViewHolder -> {
                post?.let { item ->
                    holder.txtVote.text = item.votes_count.toString()
                    holder.txtTotalComment.text = item.comments_count.toString()
                    holder.txtTitle.text = item.title
                    holder.txtContent.text = item.content
                    holder.txtName.text = item.user?.username
                    Glide.with(holder.itemView.context)
                        .load(item.user?.avatarurl)
                        .placeholder(R.drawable.avartar_profile)
                        .error(R.drawable.avartar_profile)
                        .into(holder.imgProfile)
                }
            }
            is CommentBarViewHolder -> {

            }
            is CommentItemViewHolder -> {
                val item = commentModelList[position - 3]
                holder.txtCommentName.text = item.user?.username
                holder.txtCommentContent.text = item.comment
                val imgProfile = holder.itemView.findViewById<ImageView>(R.id.imgCommentProfile)
                Glide.with(holder.itemView.context)
                    .load(item.user?.avatarurl)
                    .placeholder(R.drawable.avartar_profile)
                    .into(imgProfile)
            }
        }
    }

    override fun getItemCount(): Int = commentModelList.size + 3

    class AppBarViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    class PostItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        var txtContent = itemView.findViewById<TextView>(R.id.txtCommentContent)
        var txtName = itemView.findViewById<TextView>(R.id.txtCommentName)
        var imgProfile = itemView.findViewById<ImageView>(R.id.imgCommentProfile)
        var txtSeeMore = itemView.findViewById<TextView>(R.id.txtSeeMore)
        var txtVote = itemView.findViewById<TextView>(R.id.txtVote)
        var txtTotalComment = itemView.findViewById<TextView>(R.id.txtTotalComment)
    }
    class CommentBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
    class CommentItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgCommentProfile = itemView.findViewById<ImageView>(R.id.imgCommentProfile)
        var txtCommentName = itemView.findViewById<TextView>(R.id.txtCommentName)
        var txtCommentContent = itemView.findViewById<TextView>(R.id.txtCommentContent)
    }
}
