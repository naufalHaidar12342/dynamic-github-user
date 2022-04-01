package xyz.heydarrn.dynamicgithubuserapp.model.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import xyz.heydarrn.dynamicgithubuserapp.databinding.UserCardBinding
import xyz.heydarrn.dynamicgithubuserapp.model.UserFollowingInfoResponseItem

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    private val followingList=ArrayList<UserFollowingInfoResponseItem>()

    fun setFollowingListForAdapter(following:ArrayList<UserFollowingInfoResponseItem>) {
        followingList.clear()
        followingList.addAll(following)
        notifyDataSetChanged()
    }

    inner class FollowingViewHolder(private val followingBinding : UserCardBinding) : RecyclerView.ViewHolder(followingBinding.root) {
        fun bindFollowingData(followingInfo: UserFollowingInfoResponseItem){
            followingBinding.apply {
                Glide.with(itemView)
                    .load(followingInfo.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .circleCrop()
                    .into(imageviewUser)
                githubuserUsername.text=followingInfo.login

                buttonVisitProfileInBrowser.setOnClickListener {
                    val showFollowerProfileInBrowser= Intent(Intent.ACTION_VIEW, Uri.parse(followingInfo.htmlUrl))
                    itemView.context.startActivity(showFollowerProfileInBrowser)
                }
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowingAdapter.FollowingViewHolder {
        val followingView=UserCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FollowingViewHolder(followingView)
    }

    override fun onBindViewHolder(holder: FollowingAdapter.FollowingViewHolder, position: Int) {
        holder.bindFollowingData(followingList[position])
    }

    override fun getItemCount(): Int {
        return followingList.size
    }
}