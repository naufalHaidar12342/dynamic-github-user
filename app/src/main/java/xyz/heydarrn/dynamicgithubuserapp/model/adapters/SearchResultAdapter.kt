package xyz.heydarrn.dynamicgithubuserapp.model.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import xyz.heydarrn.dynamicgithubuserapp.databinding.UserCardBinding
import xyz.heydarrn.dynamicgithubuserapp.model.ItemsItem

class SearchResultAdapter:RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    private val list=ArrayList<ItemsItem>()
    private var whenUserClicked:OnSelectedUserClicked? =null

    fun setUserInfo(whenUserClicked: OnSelectedUserClicked){
        this.whenUserClicked=whenUserClicked
    }
    fun setArrayListForAdapter(users:ArrayList<ItemsItem>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class SearchResultViewHolder(private val binding: UserCardBinding):RecyclerView.ViewHolder(binding.root) {
        fun bindData(itemsItem: ItemsItem){
            binding.apply {
                Glide.with(itemView)
                    .load(itemsItem.avatarUrl)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .circleCrop()
                    .into(imageviewUser)
                githubuserUsername.text=itemsItem.login

                // send user with github profile link to browser, then load the link
                buttonVisitProfileInBrowser.setOnClickListener {
                    val showInBrowser= Intent(Intent.ACTION_VIEW, Uri.parse(itemsItem.htmlUrl))
                    itemView.context.startActivity(showInBrowser)
                }
                root.setOnClickListener {
                    whenUserClicked?.clickOnSelectedUser(itemsItem)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val userView=UserCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchResultViewHolder(userView)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnSelectedUserClicked {
        fun clickOnSelectedUser(selectedUser: ItemsItem)
    }
}