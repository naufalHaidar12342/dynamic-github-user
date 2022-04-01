package xyz.heydarrn.dynamicgithubuserapp.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.heydarrn.dynamicgithubuserapp.model.SelectedUserInfoResponse
import xyz.heydarrn.dynamicgithubuserapp.model.UserFollowersInfoResponseItem
import xyz.heydarrn.dynamicgithubuserapp.model.UserFollowingInfoResponseItem
import xyz.heydarrn.dynamicgithubuserapp.model.UserSearchResultResponse

interface ApiService {
    val fetchToken: PersonalToken.Companion
        get() = PersonalToken

    @Headers(fetchToken.TOKEN)
    @GET("search/users")
    fun getUserFromSearch(@Query("q") usernameQuery:String) : Call<UserSearchResultResponse>

    @GET ("users/{username}")
    fun getSelectedUserInfo(@Path("username")selectedUser:String) : Call<SelectedUserInfoResponse>

    @Headers(fetchToken.TOKEN)
    @GET("users/{username}/followers")
    fun getSelectedUserFollowers(@Path("username") selectedUserFollowers:String) : Call<ArrayList<UserFollowersInfoResponseItem>>

    @GET("users/{username}/following")
    fun getSelectedUserFollowing(@Path("username") selectedUserFollowing: String) :Call<ArrayList<UserFollowingInfoResponseItem>>

}