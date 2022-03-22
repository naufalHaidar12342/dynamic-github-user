package xyz.heydarrn.dynamicgithubuserapp.viewmodels

import androidx.lifecycle.ViewModel

class GithubUserViewModel:ViewModel() {

    var endpointUser="users"

    fun getUser(username:String){
        val endpoint= BASE_URL +endpointUser+"/" +username
        //https://api.github.com/users/username
    }
    companion object{
        const val BASE_URL="https://api.github.com/"
    }
}