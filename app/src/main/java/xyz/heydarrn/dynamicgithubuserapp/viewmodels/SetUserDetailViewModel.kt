package xyz.heydarrn.dynamicgithubuserapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.heydarrn.dynamicgithubuserapp.model.SelectedUserInfoResponse
import xyz.heydarrn.dynamicgithubuserapp.model.api.ApiConfig

class SetUserDetailViewModel :  ViewModel() {
    private var _showUserDetail= MutableLiveData<SelectedUserInfoResponse>()
    private val openDetailedInfo: LiveData<SelectedUserInfoResponse> = _showUserDetail

    fun setUserDetailedInfo(selectedUsername:String){
        val detailClient= ApiConfig.getApiService().getSelectedUserInfo(selectedUsername)
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

    fun setSelectedUserDetail(): LiveData<SelectedUserInfoResponse> = openDetailedInfo
}