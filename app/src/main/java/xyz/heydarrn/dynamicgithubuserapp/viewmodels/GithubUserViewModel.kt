package xyz.heydarrn.dynamicgithubuserapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.dynamicgithubuserapp.model.*
import xyz.heydarrn.dynamicgithubuserapp.model.api.ApiConfig

class GithubUserViewModel:ViewModel() {

    private var _listOfSearchedUser=MutableLiveData<ArrayList<ItemsItem>>()
    private val listOfSearchedUser:LiveData<ArrayList<ItemsItem>> = _listOfSearchedUser

    private var _loadingAnimation=MutableLiveData<Boolean>()
    val showLoadingProgress:LiveData<Boolean> = _loadingAnimation

    fun searchUserOnSubmittedText(findUser:String){
        val client=ApiConfig.getApiService().getUserFromSearch(findUser)
        client.enqueue(object : Callback<UserSearchResultResponse> {

            override fun onResponse(
                call: Call<UserSearchResultResponse>,
                response: Response<UserSearchResultResponse>
            ) {
                //always show loading animation first
                _loadingAnimation.value=true
                if (response.isSuccessful){
                    /*if successfully got response, hide loading animation
                    * and insert the response body to arrayList "items"*/
                    _loadingAnimation.value=false
                    _listOfSearchedUser.value= response.body()?.items
                }else{
                    _loadingAnimation.value=false
                    Log.d("success get response", "onResponse: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<UserSearchResultResponse>, t: Throwable) {
                Log.d("fail to get response", "onFailure: ${t.message}")
            }

        })
    }

    fun setResultForAdapter():LiveData<ArrayList<ItemsItem>> = listOfSearchedUser


    private var _showUserDetail=MutableLiveData<SelectedUserInfoResponse>()
    private val openDetailedInfo:LiveData<SelectedUserInfoResponse> = _showUserDetail

    fun setUserDetailedInfo(selectedUsername:String){
        val detailClient=ApiConfig.getApiService().getSelectedUserInfo(selectedUsername)
        detailClient.enqueue(object : Callback<SelectedUserInfoResponse> {

            override fun onResponse(
                call: Call<SelectedUserInfoResponse>,
                response: Response<SelectedUserInfoResponse>
            ) {
                if (response.isSuccessful){
                    _showUserDetail.value=response.body()
                }
                Log.d("success,1 user detail", "onResponse: ${response.message()}")
            }

            override fun onFailure(call: Call<SelectedUserInfoResponse>, t: Throwable) {
                Log.d("failed, 1 user detail", "onFailure: ${t.message}")
            }

        })
    }

    fun setSelectedUserDetail():LiveData<SelectedUserInfoResponse> = openDetailedInfo



}