package xyz.heydarrn.dynamicgithubuserapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.dynamicgithubuserapp.model.UserFollowersInfoResponseItem
import xyz.heydarrn.dynamicgithubuserapp.model.api.ApiConfig

class FollowersViewModel : ViewModel() {
    private var _showFollowerOfUser= MutableLiveData<ArrayList<UserFollowersInfoResponseItem>>()
    val showFollowerOfUser: LiveData<ArrayList<UserFollowersInfoResponseItem>> = _showFollowerOfUser

    fun setUserFollowersInfo(selectedUser:String){
        val followerClient= ApiConfig.getApiService().getSelectedUserFollowers(selectedUser)
        followerClient.enqueue(object : Callback<ArrayList<UserFollowersInfoResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<UserFollowersInfoResponseItem>>,
                response: Response<ArrayList<UserFollowersInfoResponseItem>>
            ) {
                if (response.isSuccessful) {
                    _showFollowerOfUser.postValue(response.body())
                }
                Log.d("follower success", "onResponse: ${response.message()}")
            }

            override fun onFailure(
                call: Call<ArrayList<UserFollowersInfoResponseItem>>,
                t: Throwable
            ) {
                Log.d("follower fail", "onFailure: ${t.message}")
            }

        })
    }

    fun setSelectedUserFollowersInfo(): LiveData<ArrayList<UserFollowersInfoResponseItem>> = showFollowerOfUser
}