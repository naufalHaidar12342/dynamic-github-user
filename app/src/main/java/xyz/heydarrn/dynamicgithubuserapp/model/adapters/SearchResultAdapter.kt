package xyz.heydarrn.dynamicgithubuserapp.model.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import xyz.heydarrn.dynamicgithubuserapp.R
import xyz.heydarrn.dynamicgithubuserapp.model.ItemsItem

class SearchResultAdapter(private val listSearchedUser:List<ItemsItem>):RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    fun getFollowers(username:String){

    }
    class SearchResultViewHolder(views:View):RecyclerView.ViewHolder(views) {
        var profilePicture:ImageView=views.findViewById(R.id.imageview_user)
        var githubUsername:TextView=views.findViewById(R.id.githubuser_username)
        var profileLink:Button=views.findViewById(R.id.button_visit_profile_in_browser)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val userView=LayoutInflater.from(parent.context).inflate(R.layout.user_card,parent,false)
        return SearchResultViewHolder(userView)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val searchResult=listSearchedUser[position]

        holder.apply {
            Glide.with(holder.itemView.context)
                .load(searchResult.avatarUrl)
                .circleCrop()
                .into(holder.profilePicture)
            githubUsername.text=searchResult.login
            profileLink.setOnClickListener {
                val openBrowser=Intent(Intent.ACTION_VIEW, Uri.parse(searchResult.htmlUrl))
                it.context.startActivity(openBrowser)
            }


        }
    }

    override fun getItemCount(): Int {
        return listSearchedUser.size
    }
}