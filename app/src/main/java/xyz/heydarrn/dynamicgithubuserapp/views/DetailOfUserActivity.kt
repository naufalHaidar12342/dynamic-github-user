package xyz.heydarrn.dynamicgithubuserapp.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import xyz.heydarrn.dynamicgithubuserapp.R
import xyz.heydarrn.dynamicgithubuserapp.databinding.ActivityDetailOfUserBinding
import xyz.heydarrn.dynamicgithubuserapp.model.adapters.TabSectionAdapter
import xyz.heydarrn.dynamicgithubuserapp.viewmodels.SetUserDetailViewModel

class DetailOfUserActivity : AppCompatActivity() {
    private lateinit var userDetailBind:ActivityDetailOfUserBinding
    private val viewModel by viewModels<SetUserDetailViewModel>()
    private var receiveUsername:String? = null
    private var followerBundle=Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_of_user)

        userDetailBind= ActivityDetailOfUserBinding.inflate(layoutInflater)
        setContentView(userDetailBind.root)

        setTabLayout()
        showsUser()

    }

    private fun showsUser(){
        //receive intent, sent from main activity
        receiveUsername=intent.getStringExtra(EXTRA_USERNAME)

        followerBundle.putString(FollowingAndFollowerFragment.USER_FOLLOWERS,receiveUsername)

        //we got username, then pass it/feed it into setUserDetailedInfo()
        receiveUsername?.let { usernameChosen ->
            viewModel.setUserDetailedInfo(usernameChosen)
        }
        //observer for selectedUser data
        viewModel.setSelectedUserDetail().observe(this) { observeUsername ->
            if (observeUsername != null) {
                userDetailBind.apply {

                    Glide.with(this@DetailOfUserActivity)
                        .load(observeUsername.avatarUrl)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .circleCrop()
                        .into(userImageDetailScreen)

                    if (observeUsername.name!=null){
                        textviewFullnameDetailScreen.text=observeUsername.name
                    }else{
                        textviewFullnameDetailScreen.text=resources.getString(R.string.fullname_got_null_response_template)
                    }

                    textviewUsernameDetailScreen.text=resources.getString(R.string.username_template,observeUsername.login)

                    // usually, user on github does not fill their location or company information,
                    // so, we need to check it
                    if (observeUsername.location!=null ){
                        textviewUserLocationDetailScreen.text=observeUsername.location
                    }else{
                        textviewUserLocationDetailScreen.text=getString(R.string.location_got_null_response_template)
                    }

                    if (observeUsername.company!=null){
                        userCompanyDetailScreen.text=observeUsername.company
                    }else{
                        userCompanyDetailScreen.text=getString(R.string.company_got_null_response_template)
                    }
                    // show how many PUBLIC repositories owned by selected user
                    userRepositoryDetailView.text=resources.getString(R.string.repository_string_template,observeUsername.publicRepos.toString())
                }
            }
        }
    }

    // a function to set tabs title and fragment to detail activity,
    // related to each tab
    private fun setTabLayout(){
        val tabSection=TabSectionAdapter(this,followerBundle)
        val viewPagers:ViewPager2=userDetailBind.viewPagerFollowingFollowers
        viewPagers.adapter=tabSection

        val tabs:TabLayout=userDetailBind.followingFollowersDetail
        TabLayoutMediator(tabs,viewPagers) {tab, position ->
            tab.text=resources.getString(TAB_NAMES[position])
        }.attach()

    }

    //constant value for selected username and tab title/name
    companion object{
        const val EXTRA_USERNAME="username of github user goes here"
        private val TAB_NAMES= intArrayOf(
            R.string.followers_tab,
            R.string.following_tab
        )
    }
}