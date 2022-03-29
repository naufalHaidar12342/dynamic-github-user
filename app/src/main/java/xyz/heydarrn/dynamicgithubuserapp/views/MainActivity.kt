package xyz.heydarrn.dynamicgithubuserapp.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.heydarrn.dynamicgithubuserapp.R
import xyz.heydarrn.dynamicgithubuserapp.databinding.ActivityMainBinding
import xyz.heydarrn.dynamicgithubuserapp.model.ItemsItem
import xyz.heydarrn.dynamicgithubuserapp.model.adapters.SearchResultAdapter
import xyz.heydarrn.dynamicgithubuserapp.viewmodels.GithubUserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMainActivity: ActivityMainBinding
    private val githubUserViewModel by viewModels<GithubUserViewModel>()
    private lateinit var resultAdapter:SearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindingMainActivity= ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)

        getInsertedUsername()
        setRecViewAdapter()
    }


    private fun setRecViewAdapter(){
        resultAdapter= SearchResultAdapter()
        resultAdapter.notifyDataSetChanged()
        //when user selected one of search result, show the result in detailed manner
        //on detailOfUserActivity
        resultAdapter.setUserInfo(object : SearchResultAdapter.OnSelectedUserClicked {
            override fun clickOnSelectedUser(selectedUser: ItemsItem) {
                val showsDetail=Intent(this@MainActivity,DetailOfUserActivity::class.java)
                showsDetail.putExtra(DetailOfUserActivity.EXTRA_USERNAME,selectedUser.login)
                startActivity(showsDetail)
            }

        })
        bindingMainActivity.apply {
            recyclerviewSearchResult.layoutManager=LinearLayoutManager(this@MainActivity)
            recyclerviewSearchResult.setHasFixedSize(true)
            recyclerviewSearchResult.adapter=resultAdapter
        }
        githubUserViewModel.setResultForAdapter().observe(this) {
            if (it != null) {
                resultAdapter.setArrayListForAdapter(it)
                showLoadingAnimation(false)
            }
        }

    }

    private fun getInsertedUsername(){
        bindingMainActivity.apply { 
            searchviewUserGithub.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    githubUserViewModel.searchUserOnSubmittedText(p0!!)
                    searchviewUserGithub.clearFocus()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    showLoadingAnimation(true)
                    return false
                }

            })
        }
    }

    private fun showLoadingAnimation(loadingState:Boolean){
        when(loadingState){
            true -> bindingMainActivity.searchProgress.visibility=View.VISIBLE
            false -> bindingMainActivity.searchProgress.visibility=View.GONE
        }
    }
}