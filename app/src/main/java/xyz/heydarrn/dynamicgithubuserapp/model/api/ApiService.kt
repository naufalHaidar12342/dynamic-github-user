package xyz.heydarrn.dynamicgithubuserapp.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.heydarrn.dynamicgithubuserapp.model.SelectedUserInfoResponse
import xyz.heydarrn.dynamicgithubuserapp.model.UserFollowersInfoResponse
import xyz.heydarrn.dynamicgithubuserapp.model.UserFollowingInfoResponse
import xyz.heydarrn.dynamicgithubuserapp.model.UserSearchResultResponse

interface ApiService {
    val fetchToken: PersonalToken.Companion
        get() = PersonalToken

    @GET("search/users")
    @Headers(fetchToken.TOKEN)
    fun getUserFromSearch(@Query("q") usernameQuery:String) : Call<UserSearchResultResponse>

    @GET ("users/{username}")
    fun getSelectedUserInfo(@Path("username")selectedUser:String) : Call<SelectedUserInfoResponse>

    @GET("users/{username}/followers")
    fun getSelectedUserFollowers(@Path("followers") selectedUserFollowers:String) : Call<UserFollowersInfoResponse>

    @GET("user/{username}/following")
    fun getSelectedUserFollowing(@Path("following") selectedUserFollowing: String) :Call<UserFollowingInfoResponse>

}