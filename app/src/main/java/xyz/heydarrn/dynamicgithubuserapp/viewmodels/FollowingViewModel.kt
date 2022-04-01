package xyz.heydarrn.dynamicgithubuserapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.dynamicgithubuserapp.model.UserFollowingInfoResponseItem
import xyz.heydarrn.dynamicgithubuserapp.model.api.ApiConfig

class FollowingViewModel : ViewModel() {
    private var _userFollowingInfo=MutableLiveData<ArrayList<UserFollowingInfoResponseItem>>()
    val userFollowingInfo:LiveData<ArrayList<UserFollowingInfoResponseItem>> = _userFollowingInfo

    fun showFollowingInfo(userFollowing:String){
        val followingClient=ApiConfig.getApiService().getSelectedUserFollowing(userFollowing)
        followingClient.enqueue(object : Callback<ArrayList<UserFollowingInfoResponseItem>> {

            override fun onResponse(
                call: Call<ArrayList<UserFollowingInfoResponseItem>>,
                response: Response<ArrayList<UserFollowingInfoResponseItem>>
            ) {
                if (response.isSuccessful){
                    _userFollowingInfo.postValue(response.body())
                }
                Log.d("following success", "onResponse: ${response.message()} ")
            }

            override fun onFailure(
                call: Call<ArrayList<UserFollowingInfoResponseItem>>,
                t: Throwable
            ) {
                Log.d("following fail", "onFailure: ${t.message}")
            }

        })
    }

    fun monitorFollowingInfo():LiveData<ArrayList<UserFollowingInfoResponseItem>> {
        return userFollowingInfo
    }
}