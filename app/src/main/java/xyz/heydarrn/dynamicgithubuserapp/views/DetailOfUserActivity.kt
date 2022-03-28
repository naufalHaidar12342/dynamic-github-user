package xyz.heydarrn.dynamicgithubuserapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import xyz.heydarrn.dynamicgithubuserapp.R
import xyz.heydarrn.dynamicgithubuserapp.databinding.ActivityDetailOfUserBinding
import xyz.heydarrn.dynamicgithubuserapp.model.ItemsItem
import xyz.heydarrn.dynamicgithubuserapp.model.adapters.TabSectionAdapter
import xyz.heydarrn.dynamicgithubuserapp.viewmodels.GithubUserViewModel

class DetailOfUserActivity : AppCompatActivity() {
    private lateinit var userDetailBind:ActivityDetailOfUserBinding
    private val viewModel by viewModels<GithubUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_of_user)

        userDetailBind= ActivityDetailOfUserBinding.inflate(layoutInflater)
        setContentView(userDetailBind.root)

        showsUser()
        setTabLayout()

    }

    private fun showsUser(){
        val receiveUsername=intent.getStringExtra(EXTRA_USERNAME)
        receiveUsername?.let {
            viewModel.setUserDetailedInfo(it)
        }

        viewModel.setSelectedUserDetail().observe(this) { observeUsername ->
            if (observeUsername != null) {
                userDetailBind.apply {
                    Glide.with(this@DetailOfUserActivity)
                        .load(observeUsername.avatarUrl)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .circleCrop()
                        .into(userImageDetailScreen)

                    textviewFullnameDetailScreen.text=observeUsername.name
                    textviewUsernameDetailScreen.text=resources.getString(R.string.username_template,observeUsername.login)
                    if (observeUsername.location!=null && observeUsername.company !=null){
                        textviewUserLocationDetailScreen.text=observeUsername.location
                        userCompanyDetailScreen.text=observeUsername.company

                    }else{
                        textviewUserLocationDetailScreen.text="Location are hidden by user"
                        userCompanyDetailScreen.text="Company are hidden by user"
                    }
                    userRepositoryDetailView.text=resources.getString(R.string.repository_string_template,observeUsername.publicRepos.toString())

                }
            }
        }
    }
    private fun setTabLayout(){
        val tabSection=TabSectionAdapter(this)
        val viewPagers:ViewPager2=userDetailBind.viewPagerFollowingFollowers
        viewPagers.adapter=tabSection
        val tabs:TabLayout=userDetailBind.followingFollowersDetail
        TabLayoutMediator(tabs,viewPagers) {tab, position ->
            tab.text=resources.getString(TAB_NAMES[position])
        }.attach()
    }
    companion object{
        const val EXTRA_USERNAME="username of github user goes here"
        private val TAB_NAMES= intArrayOf(
            R.string.followers_tab,
            R.string.following_tab
        )
    }
}