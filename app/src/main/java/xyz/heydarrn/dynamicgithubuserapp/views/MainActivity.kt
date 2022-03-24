package xyz.heydarrn.dynamicgithubuserapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.heydarrn.dynamicgithubuserapp.R
import xyz.heydarrn.dynamicgithubuserapp.databinding.ActivityMainBinding
import xyz.heydarrn.dynamicgithubuserapp.viewmodels.GithubUserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMainActivity: ActivityMainBinding
    private val githubUserViewModel by viewModels<GithubUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun setRecViewAdapter(){
        val recViewLayoutManager=LinearLayoutManager(this)
        bindingMainActivity.recyclerviewSearchResult.layoutManager=recViewLayoutManager
        val itemDecoration=DividerItemDecoration(this ,recViewLayoutManager.orientation)
        bindingMainActivity.recyclerviewSearchResult.addItemDecoration(itemDecoration)
    }

//    fun getInsertedText(){
//        bindingMainActivity.apply {
//            searchviewUserGithub.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//                android.widget.SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    TODO("Not yet implemented")
//                }
//
//            })
//        }
//    }

    private fun showLoadingAnimation(loadingState:Boolean){
        bindingMainActivity.searchProgress.visibility.apply {
            if (loadingState) View.VISIBLE else View.INVISIBLE
        }
    }
}