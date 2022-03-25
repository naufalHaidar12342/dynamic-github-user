package xyz.heydarrn.dynamicgithubuserapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
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
    private var resultAdapter :SearchResultAdapter=SearchResultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindingMainActivity= ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMainActivity.root)

        getInsertedText()
        setRecViewAdapter()
        resultAdapter.notifyDataSetChanged()

        githubUserViewModel.setResultForAdapter().observe(this, Observer { observing ->
            if (observing==null){
                resultAdapter.observeList(observing as ArrayList<ItemsItem>)
                showLoadingAnimation(false)
            }
        })
    }

    private fun setRecViewAdapter(){
        val recViewLayoutManager=LinearLayoutManager(this)
//        val itemDecoration=DividerItemDecoration(this ,recViewLayoutManager.orientation)
        bindingMainActivity.recyclerviewSearchResult.apply {
            this.layoutManager=recViewLayoutManager
            this.adapter=resultAdapter
        }
//        bindingMainActivity.recyclerviewSearchResult.addItemDecoration(itemDecoration)

    }

    fun getInsertedText(){
        bindingMainActivity.apply {

            searchviewUserGithub.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        githubUserViewModel.searchUser(query)

                        githubUserViewModel.showLoadingProgress.observe(this@MainActivity)
                        { thisAnimation ->
                            showLoadingAnimation(thisAnimation)
                        }
                    }

                    searchviewUserGithub.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                   return false
                }

            })
        }
    }

    private fun showLoadingAnimation(loadingState:Boolean){
        when(loadingState){
            true -> bindingMainActivity.searchProgress.visibility=View.VISIBLE
            else -> bindingMainActivity.searchProgress.visibility=View.INVISIBLE
        }
    }
}