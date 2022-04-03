package xyz.heydarrn.dynamicgithubuserapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.dynamicgithubuserapp.model.ItemsItem
import xyz.heydarrn.dynamicgithubuserapp.model.UserSearchResultResponse
import xyz.heydarrn.dynamicgithubuserapp.model.api.ApiConfig

class GithubUserViewModel:ViewModel() {

    private var _listOfSearchedUser=MutableLiveData<ArrayList<ItemsItem>>()
    private val listOfSearchedUser:LiveData<ArrayList<ItemsItem>> = _listOfSearchedUser

    fun searchUserOnSubmittedText(findUser:String){
        val client=ApiConfig.getApiService().getUserFromSearch(findUser)
        client.enqueue(object : Callback<UserSearchResultResponse> {

            override fun onResponse(
                call: Call<UserSearchResultResponse>,
                response: Response<UserSearchResultResponse>
            ) {
                if (response.isSuccessful){
                    /*if successfully got response, insert the response body to arrayList "items"*/
                    _listOfSearchedUser.value= response.body()?.items
                }else{
                    Log.d("success get response", "onResponse: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<UserSearchResultResponse>, t: Throwable) {
                Log.d("fail to get response", "onFailure: ${t.message}")
            }

        })
    }

    fun setResultForAdapter():LiveData<ArrayList<ItemsItem>> = listOfSearchedUser





}