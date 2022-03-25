package xyz.heydarrn.dynamicgithubuserapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.dynamicgithubuserapp.databinding.ActivityMainBinding
import xyz.heydarrn.dynamicgithubuserapp.model.ItemsItem
import xyz.heydarrn.dynamicgithubuserapp.model.UserSearchResultResponse
import xyz.heydarrn.dynamicgithubuserapp.model.api.ApiConfig

class GithubUserViewModel:ViewModel() {
    private lateinit var binding: ActivityMainBinding

    private val _listOfSearchedUser=MutableLiveData<List<ItemsItem>>()
    val listOfSearchedUser:LiveData<List<ItemsItem>> = _listOfSearchedUser

    private val _loadingAnimation=MutableLiveData<Boolean>()
    val showLoadingProgress:LiveData<Boolean> = _loadingAnimation

    private val _usernameEntered= String()
    val usernameEntered:String=_usernameEntered

    fun searchUser(queriedUsername:String){
        val performSearch=ApiConfig.getApiService().getUserFromSearch(queriedUsername)
        performSearch.enqueue(object : Callback<UserSearchResultResponse> {
            //when successfully got response from API, do these
            override fun onResponse(
                call: Call<UserSearchResultResponse>,
                response: Response<UserSearchResultResponse>
            ) {
                _loadingAnimation.value=true
                if (response.isSuccessful){
                    _loadingAnimation.value=false
                    _listOfSearchedUser.value=response.body()?.items
                }else{
                    Log.d("response fail", "onResponse: ${response.message().toString()}")
                }
            }
            //when we fail to get response from API, do this
            override fun onFailure(call: Call<UserSearchResultResponse>, t: Throwable) {
                Log.d("onFailure log", "onFailure: ${t.message}")
            }

        })
    }

    fun setResultForAdapter():LiveData<List<ItemsItem>>{
        return listOfSearchedUser
    }

}