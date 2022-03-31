package xyz.heydarrn.dynamicgithubuserapp.model.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import xyz.heydarrn.dynamicgithubuserapp.databinding.UserCardBinding
import xyz.heydarrn.dynamicgithubuserapp.model.UserFollowersInfoResponse
import xyz.heydarrn.dynamicgithubuserapp.model.UserFollowersInfoResponseItem

class FollowersAdapter:RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    private val followersList=ArrayList<UserFollowersInfoResponseItem>()

    fun setFollowersListForAdapter(followers:ArrayList<UserFollowersInfoResponseItem>){
        followersList.clear()
        followersList.addAll(followers)
        notifyDataSetChanged()
    }

    inner class FollowersViewHolder(private val followerBinding: UserCardBinding) :RecyclerView.ViewHolder(followerBinding.root) {
        fun bindFollowersData(followerInfo: UserFollowersInfoResponseItem){
            followerBinding.apply {
                Glide.with(itemView)
                    .load(followerInfo.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .circleCrop()
                    .into(imageviewUser)
                githubuserUsername.text=followerInfo.login

                buttonVisitProfileInBrowser.setOnClickListener {
                    val showFollowerProfileInBrowser=Intent(Intent.ACTION_VIEW, Uri.parse(followerInfo.htmlUrl))
                    itemView.context.startActivity(showFollowerProfileInBrowser)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val followersView=UserCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FollowersViewHolder(followersView)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bindFollowersData(followersList[position])
    }

    override fun getItemCount(): Int {
        return followersList.size
    }
}