package xyz.heydarrn.dynamicgithubuserapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.heydarrn.dynamicgithubuserapp.model.ItemsItem

class GithubUserViewModel:ViewModel() {

    private val _listSearchedUser=MutableLiveData<List<ItemsItem>>()
    val listSearchedUser:LiveData<List<ItemsItem>> = _listSearchedUser

    private val _isLoading=MutableLiveData<Boolean>()
    val showLoading:LiveData<Boolean> = _isLoading

    companion object{
        const val BASE_URL="https://api.github.com/"
    }
}