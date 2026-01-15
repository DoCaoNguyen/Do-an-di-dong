package com.doan.social.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doan.social.R
import com.doan.social.model.Comment


class PostDetailAdapter(private val commentList: MutableList<Comment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_POST_ITEM = 0
    private val VIEW_TYPE_COMMENT_BAR = 1
    private val VIEW_TYPE_COMMENT_ITEM = 2

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> VIEW_TYPE_POST_ITEM
            1 -> VIEW_TYPE_COMMENT_BAR
            else -> VIEW_TYPE_COMMENT_ITEM
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_POST_ITEM -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_layout,parent,false)
                PostItemViewHolder(itemView)
            }
            VIEW_TYPE_COMMENT_BAR -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.postbar_layout,parent,false)
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
            is CommentItemViewHolder -> {
                val item = commentList[position - 2]
            }

            is CommentBarViewHolder -> {

            }
        }
    }

    override fun getItemCount(): Int = commentList.size + 2

    class PostItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class CommentBarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class CommentItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
