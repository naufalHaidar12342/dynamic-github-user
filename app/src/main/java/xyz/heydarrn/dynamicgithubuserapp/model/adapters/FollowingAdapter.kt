package xyz.heydarrn.dynamicgithubuserapp.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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